package org.sacada.codegen

import kotlinx.coroutines.runBlocking
import java.io.File
import org.sacada.jsonbuilder.convertFigmaData
import org.sacada.core.util.JsonParser

object CodegenMain {
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size < 3) {
            println("Usage: CodegenMain <apiKey> <fileKey> <outputDir>")
            return
        }
        val apiKey = args[0]
        val fileKey = args[1]
        val outputDir = File(args[2])

        runBlocking {
            val json = convertFigmaData(apiKey, fileKey)
            val screens = json?.let { JsonParser.parseScreens(it.toString()) }
            if (screens != null) {
                val code = ComposeCodeGenerator.generate(screens)
                outputDir.mkdirs()
                File(outputDir, "GeneratedScreens.kt").writeText(code)
                println("Generated Compose code at ${'$'}outputDir/GeneratedScreens.kt")
            } else {
                println("Failed to generate screens")
            }
        }
    }
}
