package org.sacada.core.blueprint

import org.sacada.core.model.ViewComponent
import org.sacada.core.util.getStringAttribute

/**
 * Blueprint for a TopBar component with resolved attributes.
 */
data class TopBarBlueprint(
    override val id: String,
    val variant: String,
    val paddingLeft: Int,
    val paddingRight: Int,
    val paddingTop: Int,
    val paddingBottom: Int,
    val title: String,
    val scrollBehavior: String?,
    val navigationIcon: ViewComponent?,
    val actions: List<ViewComponent>
) : Blueprint {
    companion object {
        fun from(component: ViewComponent): TopBarBlueprint {
            val variant = component.getStringAttribute("topBarType")
            val paddingLeft = component.getStringAttribute("paddingLeft").toIntOrNull() ?: 0
            val paddingRight = component.getStringAttribute("paddingRight").toIntOrNull() ?: 0
            val paddingTop = component.getStringAttribute("paddingTop").toIntOrNull() ?: 0
            val paddingBottom = component.getStringAttribute("paddingBottom").toIntOrNull() ?: 0
            val title = component.getStringAttribute("title")
            val scrollBehavior = component.getStringAttribute("scrollBehavior")
            val navigationIcon = component.children.find { it.type == "navigationIcon" }
            val actions = component.children.filter { it.type == "Action" }
            return TopBarBlueprint(
                id = component.id,
                variant = variant,
                paddingLeft = paddingLeft,
                paddingRight = paddingRight,
                paddingTop = paddingTop,
                paddingBottom = paddingBottom,
                title = title,
                scrollBehavior = scrollBehavior,
                navigationIcon = navigationIcon,
                actions = actions
            )
        }
    }
}
