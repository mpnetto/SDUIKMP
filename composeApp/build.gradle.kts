import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(projects.core)
            implementation(projects.data)
            implementation(projects.jsonbuilder)
            implementation(projects.codegen)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.navigation.compose)
            implementation(libs.bundles.koin.compose)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}

android {
    namespace = "org.sacada.sdui"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    defaultConfig {
        applicationId = "org.sacada.sdui"
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.android.targetSdk
                .get()
                .toInt()
        versionCode = 1
        versionName = "1.0"

        val apiKey =
            project.loadLocalProperty(
                path = "local.properties",
                propertyName = "FIGMA_API_KEY",
            )
        val apiSecret =
            project.loadLocalProperty(
                path = "local.properties",
                propertyName = "FIGMA_FILE_KEY",
            )
        buildConfigField("String", "FIGMA_API_KEY", apiKey)
        buildConfigField("String", "FIGMA_FILE_KEY", apiSecret)
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

// Task to generate Compose code directly from Figma
tasks.register("generateComposeCodeFromFigma") {
    group = "codegen"
    description = "Generate Compose code from Figma designs"
    dependsOn(":codegen:desktopJar")

    doLast {
        val apiKey = loadLocalProperty("local.properties", "FIGMA_API_KEY")
        val fileKey = loadLocalProperty("local.properties", "FIGMA_FILE_KEY")
        val outputDir = projectDir.resolve("src/commonMain/kotlin/generated")
        outputDir.mkdirs()

        javaexec {
            mainClass.set("org.sacada.codegen.CodegenMain")
            val runtimeCp = project(":codegen").configurations.getByName("desktopRuntimeClasspath")
            val jar = project(":codegen").tasks.getByName("desktopJar").outputs.files
            classpath = runtimeCp + jar
            args(apiKey, fileKey, outputDir.absolutePath)
        }
    }
}

fun Project.loadLocalProperty(
    path: String,
    propertyName: String,
): String {
    val localProperties = Properties()
    val localPropertiesFile = project.rootProject.file(path)
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
        return localProperties.getProperty(propertyName)
    } else {
        throw GradleException("can not find property : $propertyName")
    }
}
