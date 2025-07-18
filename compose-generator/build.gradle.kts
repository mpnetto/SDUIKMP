import java.util.Properties

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(projects.figma2sdui)
    implementation(projects.data)
    implementation(projects.jsonbuilder)
    implementation(projects.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.poet)
    implementation(libs.kotlinx.coroutines.core)
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:${libs.versions.kotlin.get()}")
}

val generateFigmaCompose by tasks.registering(JavaExec::class) {
    group = "figma"
    description = "Generate Compose code from Figma file"
    mainClass.set("org.sacada.composegenerator.GenerateKt")
    classpath = sourceSets["main"].runtimeClasspath
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

    args(
        apiKey,
        apiSecret,
        layout.buildDirectory
            .dir("generated/figmaCompose")
            .get()
            .asFile.absolutePath,
        project.rootProject.file("data/src/commonMain/kotlin/org/sacada/data/ui/screen/RenderScreen.kt").absolutePath,
    )
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
