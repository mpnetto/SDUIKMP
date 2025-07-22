package org.sacada.data.ui.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.sacada.annotation.RegisterComponent
import org.sacada.core.blueprint.ButtonBlueprint
import org.sacada.core.blueprint.Blueprint
import org.sacada.data.navigation.LocalNavigator
import org.sacada.data.ui.components.Component
import org.sacada.data.ui.components.RenderComponent

@RegisterComponent
object ButtonRenderer : Component.Renderer {
    @Composable
    override fun Render(
        blueprint: Blueprint,
        modifier: Modifier?,
    ) {
        blueprint as ButtonBlueprint
        val navController = LocalNavigator.current

        Button(onClick = {
            when (blueprint.action?.type) {
                "NAVIGATE" ->
                    blueprint.action.destination?.let {
                        navController.navigate("screen/$it")
                    }
                "BACK" -> navController.popBackStack()
            }
        }) {
            blueprint.children.forEach { child ->
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
        ButtonRenderer.Render(ButtonBlueprint.from(sampleComponent))
    }
}
