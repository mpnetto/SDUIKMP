package com.sacada.data.ui.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sacada.annotation.RegisterComponent
import com.sacada.core.model.ViewComponent
import com.sacada.data.ui.components.Component
import com.sacada.data.ui.components.RenderComponent
import com.sacada.data.util.performAction
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.compose.ui.tooling.preview.Preview

@RegisterComponent
object ButtonRenderer : Component.Renderer {
    @Composable
    override fun Render(
        component: ViewComponent,
        modifier: Modifier?,
    ) {
        Button(onClick = { component.performAction() }) {
            component.children.forEach { child ->
                RenderComponent(child)
            }
        }
    }
}

@Preview()
@Composable
fun PreviewRenderButton_Varied() {
    val sampleComponent =
        ViewComponent(
            type = "Button",
            children =
                listOf(
                    ViewComponent(
                        type = "Text",
                        attributes = mapOf("content" to JsonPrimitive("Click Me")),
                    ),
                ),
        )

    MaterialTheme {
        ButtonRenderer.Render(component = sampleComponent)
    }
}
