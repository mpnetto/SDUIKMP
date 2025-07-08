package org.sacada.data.ui.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sacada.annotation.RegisterComponent
import org.sacada.core.model.ViewComponent
import org.sacada.core.util.getStringAttribute
import org.sacada.data.ui.components.Component
import org.sacada.data.ui.components.RenderComponent

@RegisterComponent
object ListRenderer : Component.Renderer {
    @Composable
    override fun Render(
        component: ViewComponent,
        modifier: Modifier?
    ) {
        val itemSpacing = component.getStringAttribute("itemSpacing").toIntOrNull()?.dp ?: 0.dp
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(itemSpacing),
            modifier = modifier ?: Modifier
        ) {
            items(component.children) { child ->
                RenderComponent(child)
            }
        }
    }
}
