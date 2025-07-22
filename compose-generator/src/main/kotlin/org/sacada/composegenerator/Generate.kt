package org.sacada.composegenerator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.sacada.core.model.ViewComponent
import org.sacada.core.model.ViewScreen
import org.sacada.core.util.JsonParser
import org.sacada.data.ui.components.ComponentRegistry
import org.sacada.figma2sdui.fetchFigmaData
import org.sacada.jsonbuilder.converter.FigmaJsonConverter
import org.sacada.jsonbuilder.generator.JsonBuilderVisitor
import java.io.File

/**
 * Entry point for generating Compose code from a Figma file.
 * Arguments: apiKey fileKey outputDir renderScreenFile componentsDir
 */
fun main(args: Array<String>) {
    if (args.size < 5) {
        println(
            "Usage: generate <apiKey> <fileKey> <outputDir> " +
                "<renderScreenFile> <componentsDir>",
        )
        return
    }
    val apiKey = args[0]
    val fileKey = args[1]
    val outputDir = File(args[2])
    val renderScreenFile = File(args[3])
    val componentsDir = File(args[4])

    println("Generating Compose code for Figma in folder: $outputDir")

    runBlocking {
        generateCompose(
            apiKey,
            fileKey,
            outputDir,
            renderScreenFile,
            componentsDir,
        )
    }
}

suspend fun generateCompose(
    apiKey: String,
    fileKey: String,
    outputDir: File,
    renderScreenFile: File,
    componentsDir: File,
) {
    val renderScreenTemplate = TemplateExtractor.extractTemplate(renderScreenFile, "RenderScreen")
    val rendererTemplates = loadRendererTemplates(componentsDir)
    val renderComponentTemplate = loadRenderComponentTemplate(componentsDir)
    val rootDocument = fetchFigmaData(apiKey, fileKey) ?: return

    val json = FigmaJsonConverter(JsonBuilderVisitor()).convert(rootDocument)
    val screens = JsonParser.parseScreens(json.toString()) ?: return

    screens.screens.forEach { screen ->
        val funcName = screen.name?.replace(" ", "_")?.replace(":", "_") ?: "Screen"

        val screenJson = Json.encodeToString(screen)

        // Processa componentes TopBar e gera código específico
        processTopBarComponents(screen, outputDir)

        val codeBlock =
            CodeBlock
                .builder()
                .addStatement("val json = %P", screenJson)
                .addStatement(
                    "val screen = %T.decodeFromString(%T.serializer(), json)",
                    Json::class,
                    ViewScreen::class,
                ).add(helperCode(rendererTemplates))
                .add(renderScreenTemplate.body)
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

        val rendererImports = rendererTemplates.flatMap { it.info.imports }
        val imports = (renderScreenTemplate.imports + rendererImports + renderComponentTemplate.imports).distinct()
        imports.forEach { imp ->
            val pkg = imp.substringBeforeLast('.')
            val name = imp.substringAfterLast('.')
            fileSpecBuilder.addImport(pkg, name)
        }

        val fileSpec = fileSpecBuilder.build()

        fileSpec.writeTo(outputDir)
    }
}

private fun helperCode(renderers: List<RendererTemplate>): String {
    val builder = StringBuilder()

    renderers.forEach { renderer ->
        val body = renderer.info.body.prependIndent("    ")
        builder.appendLine("@Composable")
        builder.appendLine("fun ${renderer.name}(component: ViewComponent, modifier: Modifier? = null) {")
        builder.appendLine(body)
        builder.appendLine("}")
        builder.appendLine()
    }

    builder.appendLine("@Composable")
    builder.appendLine("fun RenderComponent(component: ViewComponent, modifier: Modifier? = null) {")
    builder.appendLine("    when (component.type.lowercase()) {")
    renderers.forEach { renderer ->
        val type = renderer.name.removeSuffix("Renderer").lowercase()
        builder.appendLine("        \"$type\" -> ${renderer.name}(component, modifier)")
    }
    builder.appendLine("        else -> RenderUnsupported(component)")
    builder.appendLine("    }")
    builder.appendLine("}")
    builder.appendLine()

    // Adiciona função para gerar código usando CodeGenerator
    builder.appendLine("fun generateComponentCode(component: ViewComponent): String {")
    builder.appendLine("    return try {")
    builder.appendLine("        ComponentRegistry.getCodeGenerator(component.type).generateCode(component)")
    builder.appendLine("    } catch (e: Exception) {")
    builder.appendLine("        \"// Code generation not supported for component type: \${component.type}\"")
    builder.appendLine("    }")
    builder.appendLine("}")
    builder.appendLine()

    builder.appendLine("@Composable")
    builder.appendLine("fun RenderUnsupported(component: ViewComponent) {")
    builder.appendLine("    Text(text = \"Unsupported component: \${component.type}\")")
    builder.appendLine("}")

    return builder.toString().trimIndent()
}

private data class RendererTemplate(
    val name: String,
    val info: TemplateExtractor.TemplateInfo,
)

private fun loadRendererTemplates(componentsDir: File): List<RendererTemplate> =
    componentsDir
        .walkTopDown()
        .filter { it.isFile && it.name.endsWith("Renderer.kt") }
        .map { file ->
            val name = file.nameWithoutExtension
            val info = TemplateExtractor.extractTemplate(file, "Render")
            RendererTemplate(name, info)
        }.toList()

private fun loadRenderComponentTemplate(componentsDir: File): TemplateExtractor.TemplateInfo {
    val file = componentsDir.walkTopDown().first { it.name == "RenderComponent.kt" }
    return TemplateExtractor.extractTemplate(file, "RenderComponent")
}

private fun processTopBarComponents(
    screen: ViewScreen,
    outputDir: File,
) {
    fun processComponent(component: ViewComponent) {
        if (component.type.lowercase() == "topbar") {
            try {
                val codeGenerator = ComponentRegistry.getCodeGenerator("topbar")
                val generatedCode = codeGenerator.generateCode(component)

                // Salva o código gerado em um arquivo separado
                val fileName = "TopBar_${component.id}.kt"
                val file = File(outputDir, fileName)
                file.writeText(generatedCode)

                println("Generated TopBar code for component ${component.id} -> $fileName")
            } catch (e: Exception) {
                println("Failed to generate code for TopBar component ${component.id}: ${e.message}")
            }
        }

        // Processa recursivamente os componentes filhos
        component.children.forEach { child ->
            processComponent(child)
        }
    }

    // Processa os componentes da tela conforme a estrutura do ViewScreen
    screen.topBar?.let { processComponent(it) }
    screen.bottomBar?.let { processComponent(it) }
    screen.layout?.let { processComponent(it) }
}
