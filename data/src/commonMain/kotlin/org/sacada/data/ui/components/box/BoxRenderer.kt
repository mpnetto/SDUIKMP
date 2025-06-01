package org.sacada.data.ui.components.box

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sacada.annotation.RegisterComponent
import org.sacada.core.model.ViewComponent
import org.sacada.core.util.getStringAttribute
import org.sacada.data.navigation.LocalNavigator
import org.sacada.data.ui.components.Component
import org.sacada.data.ui.components.RenderComponent
import org.sacada.data.util.getPadding
import org.sacada.data.util.performAction

@RegisterComponent
object BoxRenderer : Component.Renderer {
    @Composable
    override fun Render(
        component: ViewComponent,
        modifier: Modifier?,
    ) {
        val navController = LocalNavigator.current

        val padding = remember { component.getPadding() }
        val height = component.getStringAttribute("height").toFloatOrNull()?.dp ?: 0.dp
        val width = component.getStringAttribute("width").toFloatOrNull()?.dp ?: 0.dp

        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .width(width)
                    .height(height)
                    .padding(padding)
                    .clickable {
                        component.performAction(navController)
                    },
        ) {
            component.children.forEach { child ->
                RenderComponent(child)
            }
        }
    }
}
