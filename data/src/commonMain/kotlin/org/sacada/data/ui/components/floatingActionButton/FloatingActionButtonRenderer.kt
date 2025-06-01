package org.sacada.data.ui.components.floatingActionButton

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.sacada.annotation.RegisterComponent
import org.sacada.core.model.ViewComponent
import org.sacada.core.util.getStringAttribute
import org.sacada.data.ui.components.Component
import org.sacada.data.ui.components.RenderComponent
// import org.sacada.data.ui.screen.LocalScreenViewModel
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.sacada.data.navigation.LocalNavigator
import org.sacada.data.util.performAction

@RegisterComponent
object FloatingActionButtonRenderer : Component.Renderer {
    @Composable
    override fun Render(
        component: ViewComponent,
        modifier: Modifier?,
    ) {
        val navController = LocalNavigator.current
//        val viewModel = LocalScreenViewModel.current
//        val allComponentsValid by viewModel.areAllComponentsValid.collectAsState()

        val iconName = component.getStringAttribute("iconName")
        val contentDescription = component.getStringAttribute("contentDescription")

        FloatingActionButton(
            onClick = {
//                if (allComponentsValid) {
                component.performAction(navController)
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
