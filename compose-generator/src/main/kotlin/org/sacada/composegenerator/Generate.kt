package org.sacada.composegenerator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.sacada.core.model.ViewScreen
import org.sacada.core.util.JsonParser
import org.sacada.figma2sdui.fetchFigmaData
import org.sacada.jsonbuilder.converter.FigmaJsonConverter
import org.sacada.jsonbuilder.generator.JsonBuilderVisitor
import java.io.File

/**
 * Entry point for generating Compose code from a Figma file.
 * Arguments: apiKey fileKey outputDir templateFile
 */
fun main(args: Array<String>) {
    if (args.size < 4) {
        println("Usage: generate <apiKey> <fileKey> <outputDir> <templateFile>")
        return
    }
    val apiKey = args[0]
    val fileKey = args[1]
    val outputDir = File(args[2])
    val templateFile = File(args[3])

    println("Generating Compose code for Figma in folder: $outputDir")

    runBlocking {
        generateCompose(apiKey, fileKey, outputDir, templateFile)
    }
}

suspend fun generateCompose(
    apiKey: String,
    fileKey: String,
    outputDir: File,
    templateFile: File,
) {
    val template = TemplateExtractor.extractTemplate(templateFile, "RenderScreen")
    val rootDocument = fetchFigmaData(apiKey, fileKey) ?: return

    val json = FigmaJsonConverter(JsonBuilderVisitor()).convert(rootDocument)
    val screens = JsonParser.parseScreens(json.toString()) ?: return

    screens.screens.forEach { screen ->
        val funcName = screen.name?.replace(" ", "_")?.replace(":", "_") ?: "Screen"

        val screenJson = Json.encodeToString(screen)

        val codeBlock =
            CodeBlock
                .builder()
                .addStatement("val json = %P", screenJson)
                .addStatement(
                    "val screen = %T.decodeFromString(%T.serializer(), json)",
                    Json::class,
                    ViewScreen::class,
                ).add(template.body)
                .build()

        val funSpec =
            FunSpec
                .builder(funcName)
                .addModifiers(KModifier.PUBLIC)
                .addAnnotation(ClassName("androidx.compose.runtime", "Composable"))
                .addCode(codeBlock)
                .build()

        val fileSpecBuilder =
            FileSpec
                .builder("org.sacada.generated", funcName)
                .addFunction(funSpec)
                .addImport("kotlinx.serialization.json", "Json")
                .addImport("org.sacada.core.model", "ViewScreen")
                .addImport("androidx.compose.runtime", "Composable")

        template.imports.forEach { imp ->
            val pkg = imp.substringBeforeLast('.')
            val name = imp.substringAfterLast('.')
            fileSpecBuilder.addImport(pkg, name)
        }

        val fileSpec = fileSpecBuilder.build()

        fileSpec.writeTo(outputDir)
    }
}
