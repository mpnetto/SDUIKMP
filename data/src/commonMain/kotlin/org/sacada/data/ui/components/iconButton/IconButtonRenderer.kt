package org.sacada.data.ui.components.iconButton

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.sacada.annotation.RegisterComponent
import org.sacada.core.model.ViewComponent
import org.sacada.core.util.getStringAttribute
import org.sacada.data.ui.components.Component
import org.sacada.data.util.getIconResource

@RegisterComponent
object IconButtonRenderer : Component.Renderer {
    @Composable
    override fun Render(
        component: ViewComponent,
        modifier: Modifier?,
    ) {
        val iconName = component.getStringAttribute("iconName")
        val contentDescription = component.getStringAttribute("contentDescription")

        if (iconName.isNotEmpty()) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = getIconResource(iconName),
                    contentDescription = contentDescription,
                )
            }
        }
    }
}

@Preview()
@Composable
fun PreviewRenderIconButton() {
    val testComponent =
        ViewComponent(
            type = "IconButton",
            attributes =
                mapOf(
                    "iconName" to JsonPrimitive("home"),
                    "contentDescription" to JsonPrimitive("Ajuda"),
                ),
        )

    IconButtonRenderer.Render(component = testComponent)
}
