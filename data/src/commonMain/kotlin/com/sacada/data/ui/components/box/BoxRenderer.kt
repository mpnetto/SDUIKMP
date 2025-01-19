package com.sacada.data.ui.components.box

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sacada.annotation.RegisterComponent
import com.sacada.core.model.ViewComponent
import com.sacada.core.util.getStringAttribute
import com.sacada.data.ui.components.Component
import com.sacada.data.ui.components.RenderComponent
import com.sacada.data.util.getPadding

@RegisterComponent
object BoxRenderer : Component.Renderer {
    @Composable
    override fun Render(component: ViewComponent, modifier: Modifier?) {
        val padding = remember { component.getPadding() }

        val height = component.getStringAttribute("height").toFloatOrNull()?.dp ?: 0.dp
        val width = component.getStringAttribute("width").toFloatOrNull()?.dp ?: 0.dp

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(width)
                .height(height)
                .padding(padding)
        ) {
            component.children.forEach { child ->
                RenderComponent(child)
            }
        }
    }
}
