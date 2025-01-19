package com.sacada.data.ui.components


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sacada.core.model.ViewComponent

@Composable
fun RenderComponent(component: ViewComponent, modifier: Modifier? = null) {
    val renderer = ComponentRegistry.getRenderer(component.type)

    renderer.Render(component, modifier)
}

@Composable
fun RenderUnsupported(component: ViewComponent) {
    Text(text = "Unsupported component: ${component.type}")
}
