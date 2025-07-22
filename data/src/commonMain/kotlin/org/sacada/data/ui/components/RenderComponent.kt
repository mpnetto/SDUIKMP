package org.sacada.data.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.sacada.core.blueprint.Blueprint
import org.sacada.core.model.ViewComponent

@Composable
fun RenderComponent(
    component: ViewComponent,
    modifier: Modifier? = null,
) {
    val renderer = ComponentRegistry.getRenderer(component.type)
    val blueprint = ComponentRegistry.createBlueprint(component)

    renderer.Render(blueprint, modifier)
}

@Composable
fun RenderUnsupported(component: ViewComponent) {
    Text(text = "Unsupported component: ${component.type}")
}
