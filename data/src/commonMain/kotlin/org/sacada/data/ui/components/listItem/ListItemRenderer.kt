package org.sacada.data.ui.components.listItem

import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.sacada.annotation.RegisterComponent
import org.sacada.core.model.ViewComponent
import org.sacada.data.ui.components.Component
import org.sacada.data.ui.components.RenderComponent

@RegisterComponent
object ListItemRenderer : Component.Renderer {
    @Composable
    override fun Render(
        component: ViewComponent,
        modifier: Modifier?
    ) {
        val headline = component.children.getOrNull(0)
        val supporting = component.children.getOrNull(1)

        ListItem(
            headlineContent = { headline?.let { RenderComponent(it) } },
            supportingContent = { supporting?.let { RenderComponent(it) } },
            modifier = modifier ?: Modifier
        )
    }
}
