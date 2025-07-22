package org.sacada.core.blueprint

import org.sacada.core.model.Action
import org.sacada.core.model.ViewComponent

/**
 * Blueprint for a Button component.
 */
data class ButtonBlueprint(
    override val id: String,
    val action: Action?,
    val children: List<ViewComponent>
) : Blueprint {
    companion object {
        fun from(component: ViewComponent): ButtonBlueprint =
            ButtonBlueprint(
                id = component.id,
                action = component.action,
                children = component.children
            )
    }
}
