package org.sacada.composegenerator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.CodeBlock
import kotlinx.coroutines.runBlocking
import org.sacada.figma2sdui.fetchFigmaData
import org.sacada.jsonbuilder.converter.FigmaJsonConverter
import org.sacada.jsonbuilder.generator.JsonBuilderVisitor
import org.sacada.core.util.JsonParser
import java.io.File

/**
 * Entry point for generating Compose code from a Figma file.
 * Arguments: apiKey fileKey outputDir
 */
fun main(args: Array<String>) {
    if (args.size < 3) {
        println("Usage: generate <apiKey> <fileKey> <outputDir>")
        return
    }
    val apiKey = args[0]
    val fileKey = args[1]
    val outputDir = File(args[2])

    runBlocking {
        generateCompose(apiKey, fileKey, outputDir)
    }
}

suspend fun generateCompose(apiKey: String, fileKey: String, outputDir: File) {
    val rootDocument = fetchFigmaData(apiKey, fileKey) ?: return

    val json = FigmaJsonConverter(JsonBuilderVisitor()).convert(rootDocument)
    val screens = JsonParser.parseScreens(json.toString()) ?: return

    screens.screens.forEach { screen ->
        val funcName = screen.name?.replace(" ", "_")?.replace(":", "_") ?: "Screen"
        val funSpec = FunSpec.builder(funcName)
            .addModifiers(KModifier.PUBLIC)
            .addAnnotation(ClassName("androidx.compose.runtime", "Composable"))
            .addCode(generateScreenCode(screen.layout))
            .build()

        val fileSpec = FileSpec.builder("org.sacada.generated", funcName)
            .addFunction(funSpec)
            .addImport("androidx.compose.foundation.layout", "Column")
            .addImport("androidx.compose.material3", "Text")
            .addImport("androidx.compose.runtime", "Composable")
            .build()

        fileSpec.writeTo(outputDir)
    }
}

private fun generateScreenCode(component: org.sacada.core.model.ViewComponent?): CodeBlock {
    val builder = CodeBlock.builder()
    component?.let { comp ->
        builder.add(generateComponentCode(comp))
    }
    return builder.build()
}

private fun generateComponentCode(component: org.sacada.core.model.ViewComponent): CodeBlock {
    return when (component.type) {
        "Column" -> generateColumn(component)
        "Text" -> generateText(component)
        else -> CodeBlock.of("/* Unsupported component: %L */\n", component.type)
    }
}

private fun generateColumn(component: org.sacada.core.model.ViewComponent): CodeBlock {
    val children = component.children.joinToString("\n") { child ->
        generateComponentCode(child).toString()
    }
    return CodeBlock.of("Column {\n%L\n}\n", children)
}

private fun generateText(component: org.sacada.core.model.ViewComponent): CodeBlock {
    val text = component.attributes["content"]?.toString() ?: """"""
    return CodeBlock.of("Text(text = %L)\n", text)
}
