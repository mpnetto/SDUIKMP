package org.sacada.core.blueprint

import org.sacada.core.model.Action
import org.sacada.core.model.ViewComponent
import org.sacada.core.util.getStringAttribute

data class BoxBlueprint(
    override val id: String,
    val paddingLeft: Int,
    val paddingRight: Int,
    val paddingTop: Int,
    val paddingBottom: Int,
    val height: Float,
    val width: Float,
    val action: Action?,
    val children: List<ViewComponent>
) : Blueprint {
    companion object {
        fun from(component: ViewComponent): BoxBlueprint {
            return BoxBlueprint(
                id = component.id,
                paddingLeft = component.getStringAttribute("paddingLeft").toIntOrNull() ?: 0,
                paddingRight = component.getStringAttribute("paddingRight").toIntOrNull() ?: 0,
                paddingTop = component.getStringAttribute("paddingTop").toIntOrNull() ?: 0,
                paddingBottom = component.getStringAttribute("paddingBottom").toIntOrNull() ?: 0,
                height = component.getStringAttribute("height").toFloatOrNull() ?: 0f,
                width = component.getStringAttribute("width").toFloatOrNull() ?: 0f,
                action = component.action,
                children = component.children
            )
        }
    }
}
