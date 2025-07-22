package org.sacada.core.blueprint

import kotlinx.serialization.json.jsonPrimitive
import org.sacada.core.model.ViewComponent
import org.sacada.core.util.getStringAttribute
import org.sacada.core.util.getSubAttributes

/** Blueprint for Text component. */
data class TextBlueprint(
    override val id: String,
    val content: String,
    val styleType: String?,
    val paddingLeft: Int,
    val paddingRight: Int,
    val paddingTop: Int,
    val paddingBottom: Int,
) : Blueprint {
    companion object {
        fun from(component: ViewComponent): TextBlueprint =
            TextBlueprint(
                id = component.id,
                content = component.getStringAttribute("content"),
                styleType = component.getSubAttributes("style")?.get("type")?.jsonPrimitive?.contentOrNull,
                paddingLeft = component.getStringAttribute("paddingLeft").toIntOrNull() ?: 0,
                paddingRight = component.getStringAttribute("paddingRight").toIntOrNull() ?: 0,
                paddingTop = component.getStringAttribute("paddingTop").toIntOrNull() ?: 0,
                paddingBottom = component.getStringAttribute("paddingBottom").toIntOrNull() ?: 0,
            )
    }
}
