package com.sacada.data.ui.components.row

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.sacada.annotation.RegisterComponent
import com.sacada.core.model.ViewComponent
import com.sacada.core.util.getStringAttribute
import com.sacada.data.ui.components.Component
import com.sacada.data.ui.components.RenderComponent
import com.sacada.data.util.getPadding
import com.sacada.data.util.resolveHorizontalArrangement
import com.sacada.data.util.resolveVerticalAlignment

@RegisterComponent
object RowRenderer : Component.Renderer {
    @Composable
    override fun Render(component: ViewComponent, modifier: Modifier?) {
        val horizontalArrangement = component.resolveHorizontalArrangement()
        val verticalAlignment = component.resolveVerticalAlignment()

        val padding = remember { component.getPadding() }
        val layoutSizingHorizontal = component.getStringAttribute("layoutSizingHorizontal")
        val layoutSizingVertical = component.getStringAttribute("layoutSizingVertical")

        val widthModifier = when (layoutSizingHorizontal) {
            "FILL" -> Modifier.fillMaxWidth()
            "HUG" -> Modifier.wrapContentWidth()
            else -> Modifier
        }

        val heightModifier = when (layoutSizingVertical) {
            "FILL" -> Modifier.fillMaxHeight()
            "HUG" -> Modifier.wrapContentHeight()
            else -> Modifier
        }

        Row(
            modifier = Modifier
                .then(widthModifier)
                .then(heightModifier)
                .padding(padding),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment
        ) {
            component.children.forEach { child ->
                RenderComponent(child)
            }
        }
    }
}
