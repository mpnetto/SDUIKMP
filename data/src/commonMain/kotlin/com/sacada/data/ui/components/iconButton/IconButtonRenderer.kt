package com.sacada.data.ui.components.iconButton

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sacada.annotation.RegisterComponent
import com.sacada.core.model.ViewComponent
import com.sacada.core.util.getStringAttribute
import com.sacada.data.ui.components.Component
import com.sacada.data.util.getIconResource
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.compose.ui.tooling.preview.Preview

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
            Box {
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
