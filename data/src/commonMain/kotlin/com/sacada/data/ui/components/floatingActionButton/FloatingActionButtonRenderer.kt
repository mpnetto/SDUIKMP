package com.sacada.data.ui.components.floatingActionButton

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.sacada.annotation.RegisterComponent
import com.sacada.core.model.ViewComponent
import com.sacada.core.util.getStringAttribute
import com.sacada.data.ui.components.Component
import com.sacada.data.ui.components.RenderComponent
// import com.sacada.data.ui.screen.LocalScreenViewModel
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.compose.ui.tooling.preview.Preview

@RegisterComponent
object FloatingActionButtonRenderer : Component.Renderer {
    @Composable
    override fun Render(
        component: ViewComponent,
        modifier: Modifier?,
    ) {
//        val viewModel = LocalScreenViewModel.current
//        val allComponentsValid by viewModel.areAllComponentsValid.collectAsState()

        val iconName = component.getStringAttribute("iconName")
        val contentDescription = component.getStringAttribute("contentDescription")

        FloatingActionButton(
            onClick = {
//                if (allComponentsValid) {
//                    component.action?.let { handleAction(it) }
//                }
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ) {
            component.children.forEach { child ->
                RenderComponent(child)
            }
        }
    }
}

@Preview
@Composable
fun PreviewRenderFloatingActionButton() {
    val sampleComponent =
        ViewComponent(
            type = "FloatingActionButton",
            attributes =
                mapOf(
                    "iconName" to JsonPrimitive("arrow_forward"),
                    "contentDescription" to JsonPrimitive("Add"),
                ),
        )
    FloatingActionButtonRenderer.Render(component = sampleComponent)
}
