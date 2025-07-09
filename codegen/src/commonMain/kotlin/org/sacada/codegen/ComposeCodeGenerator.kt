package org.sacada.codegen

import org.sacada.core.model.ViewScreen
import org.sacada.core.model.ViewScreens

object ComposeCodeGenerator {
    fun generate(viewScreens: ViewScreens): String {
        val builder = StringBuilder()
        builder.append("package generated\n\n")
        builder.append("import androidx.compose.runtime.Composable\n")
        builder.append("import org.sacada.core.model.ViewScreen\n")
        builder.append("import org.sacada.data.ui.components.RenderComponent\n\n")

        viewScreens.screens.forEachIndexed { index, screen ->
            val funcName = sanitize(screen.name) ?: "Screen$index"
            builder.append("@Composable\n")
            builder.append("fun $funcName(screen: ViewScreen) {\n")
            builder.append("    screen.layout?.let { RenderComponent(it) }\n")
            builder.append("}\n\n")
        }
        return builder.toString()
    }

    private fun sanitize(name: String?): String? =
        name?.replace("\\s+".toRegex(), "")
}
