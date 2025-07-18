package org.sacada.codegen

import org.sacada.core.model.ViewScreen
import org.sacada.core.model.ViewScreens
import java.io.File

object ComposeCodeGenerator {
    fun generate(viewScreens: ViewScreens, templateFile: File): String {
        val template = TemplateExtractor.extractTemplate(templateFile, "RenderScreen")
            ?: return ""
        val builder = StringBuilder()
        builder.append("package generated\n\n")
        val importSet = linkedSetOf("import androidx.compose.runtime.Composable")
        importSet.addAll(template.imports)
        importSet.add("import org.sacada.core.model.ViewScreen")
        importSet.add("import org.sacada.data.ui.components.RenderComponent")
        importSet.forEach { imp ->
            builder.append("$imp\n")
        }
        builder.append("\n")

        viewScreens.screens.forEachIndexed { index, screen ->
            val funcName = sanitize(screen.name) ?: "Screen$index"
            builder.append("@Composable\n")
            builder.append("fun $funcName(screen: ViewScreen) ${template.body}\n\n")
        }
        return builder.toString()
    }

    private fun sanitize(name: String?): String? =
        name?.replace("\\s+".toRegex(), "")
}
