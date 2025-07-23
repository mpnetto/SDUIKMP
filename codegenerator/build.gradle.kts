import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm("desktop") {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilations.all {
            compilerOptions.options.jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CodeGenerator"
            isStatic = true
        }
    }
    sourceSets {
        commonMain.dependencies {
            implementation(projects.jsonbuilder)
            implementation(projects.network)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}


val generateComposeCode by tasks.registering(JavaExec::class) {
    dependsOn(tasks.named("desktopJar"))
    group = "codegen"
    mainClass.set("org.sacada.codegenerator.CodeGenerationMainKt")
    classpath(tasks.named<Jar>("desktopJar"), configurations["desktopRuntimeClasspath"])
    args(buildDir.resolve("generated").absolutePath)
}
