package org.sacada.data.ui.components.list

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import org.sacada.annotation.RegisterComponent
import org.sacada.data.ui.components.Component
import org.sacada.data.util.convertToCamelCase
import org.sacada.figma2sdui.data.nodes.BaseComponent
import org.sacada.figma2sdui.data.nodes.Frame
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

@RegisterComponent
object ListGenerator : Component.Generator {
    override fun generateJson(
        baseComponent: BaseComponent,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?
    ): JsonObject {
        baseComponent as Frame
        return buildJsonObject {
            put("id", JsonPrimitive(baseComponent.id))
            put("type", JsonPrimitive(baseComponent.componentType.name.convertToCamelCase()))
            put(
                "attributes",
                buildJsonObject {
                    put("itemSpacing", JsonPrimitive(baseComponent.itemSpacing))
                }
            )
        }
    }
}
