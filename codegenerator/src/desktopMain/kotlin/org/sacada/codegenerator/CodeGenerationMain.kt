package org.sacada.codegenerator

import kotlinx.coroutines.runBlocking
import java.io.File
import org.sacada.jsonbuilder.convertFigmaData
import org.sacada.network.ApiClient

fun main(args: Array<String>) {
    val outputDir = File(args.getOrElse(0) { "build/generated" })
    val openAiKey = System.getenv("OPENAI_API_KEY") ?: ""
    val figmaApiKey = System.getenv("FIGMA_API_KEY") ?: ""
    val figmaFileKey = System.getenv("FIGMA_FILE_KEY") ?: ""

    fun gatherRendererSource(): String {
        val baseDir = File("../data/src/commonMain/kotlin/org/sacada/data/ui/components")
        if (!baseDir.exists()) return ""
        return baseDir.walkTopDown()
            .filter { it.isFile && it.name.endsWith("Renderer.kt") }
            .sortedBy { it.name }
            .joinToString(separator = "\n\n") { file ->
                val relative = baseDir.toPath().relativize(file.toPath()).toString()
                "// File: $relative\n" + file.readText()
            }
    }

    runBlocking {
        val json = convertFigmaData(figmaApiKey, figmaFileKey)
        if (json != null) {
            val client = ChatGptClient(ApiClient("https://api.openai.com/v1/"), openAiKey)
            val rendererCode = gatherRendererSource()
            val code = client.generateComposeForScreen(json, rendererCode)
            outputDir.mkdirs()
            File(outputDir, "ComposeScreen.kt").writeText(code ?: "")
        }
    }
}
