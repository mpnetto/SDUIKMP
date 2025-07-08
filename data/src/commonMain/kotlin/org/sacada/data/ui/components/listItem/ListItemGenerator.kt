package org.sacada.data.ui.components.listItem

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import org.sacada.annotation.RegisterComponent
import org.sacada.data.ui.components.Component
import org.sacada.data.util.convertToCamelCase
import org.sacada.figma2sdui.data.nodes.BaseComponent
import org.sacada.figma2sdui.data.nodes.Instance
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

@RegisterComponent
object ListItemGenerator : Component.Generator {
    override fun generateJson(
        baseComponent: BaseComponent,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?
    ): JsonObject {
        baseComponent as Instance
        return buildJsonObject {
            put("id", JsonPrimitive(baseComponent.id))
            put("type", JsonPrimitive(baseComponent.componentType.name.convertToCamelCase()))
            put("attributes", buildJsonObject { })
        }
    }
}
