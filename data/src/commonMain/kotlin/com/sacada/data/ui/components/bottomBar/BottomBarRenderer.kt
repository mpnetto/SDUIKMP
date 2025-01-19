package com.sacada.data.ui.components.bottomBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.sacada.annotation.RegisterComponent
import com.sacada.core.model.ViewComponent
import com.sacada.data.ui.components.Component
import com.sacada.data.ui.components.RenderComponent
import com.sacada.data.util.createActions
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.compose.ui.tooling.preview.Preview

@RegisterComponent
object BottomBarRenderer : Component.Renderer {
    @Composable
    override fun Render(component: ViewComponent, modifier: Modifier?) {
        val fabComponent =
            remember { component.children.find { it.type == "FloatingActionButton" } }
        val actions = component.createActions()
        val floatingActionButton = createFloatingActionButtonComposable(fabComponent)

        BottomAppBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
            actions = actions,
            floatingActionButton = floatingActionButton
        )
    }
}

@Composable
private fun createFloatingActionButtonComposable(
    fabComponent: ViewComponent?
): @Composable () -> Unit = {
    fabComponent?.let {
        RenderComponent(it)
    }
}

@Preview()
@Composable
fun PreviewRenderBottomBar() {
    val sampleComponent =
        ViewComponent(
            type = "BottomBar",
            children =
            listOf(
                ViewComponent(
                    type = "Action",
                    attributes =
                    mapOf(
                        "iconName" to JsonPrimitive("home"),
                        "contentDescription" to JsonPrimitive("Home")
                    )
                ),
                ViewComponent(
                    type = "FloatingActionButton",
                    attributes =
                    mapOf(
                        "iconName" to JsonPrimitive("add"),
                        "contentDescription" to JsonPrimitive("Adicionar")
                    )
                )
            )
        )
    BottomBarRenderer.Render(component = sampleComponent)
}
