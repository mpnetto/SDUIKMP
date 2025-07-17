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
}

val generateFigmaCompose by tasks.registering(JavaExec::class) {
    group = "figma"
    description = "Generate Compose code from Figma file"
    mainClass.set("org.sacada.composegenerator.GenerateKt")
    classpath = sourceSets["main"].runtimeClasspath
    args(
        project.findProperty("FIGMA_API_KEY") ?: "",
        project.findProperty("FIGMA_FILE_KEY") ?: "",
        layout.buildDirectory.dir("generated/figmaCompose").get().asFile.absolutePath,
    )
}
