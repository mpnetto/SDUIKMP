package org.sacada.data.ui.components.box

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import org.sacada.annotation.RegisterComponent
import org.sacada.data.ui.components.Component
import org.sacada.data.util.convertToCamelCase
import org.sacada.figma2sdui.data.nodes.Instance
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

@RegisterComponent
object BoxGenerator : Component.Generator {
    override fun generateJson(
        instance: Instance,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?,
    ): JsonObject {
        val boxJson =
            buildJsonObject {
                put("id", JsonPrimitive(instance.id))
                put("type", JsonPrimitive(instance.componentType.name.convertToCamelCase()))
                put(
                    "attributes",
                    buildJsonObject {
                        put("paddingLeft", JsonPrimitive(instance.paddingLeft))
                        put("paddingRight", JsonPrimitive(instance.paddingRight))
                        put("paddingTop", JsonPrimitive(instance.paddingTop))
                        put("paddingBottom", JsonPrimitive(instance.paddingBottom))
                        put("height", JsonPrimitive(instance.absoluteBoundingBox.height))
                        put("width", JsonPrimitive(instance.absoluteBoundingBox.width))
                    },
                )
            }

        val mutableMap = boxJson.toMutableMap()

        performAction?.invoke(mutableMap)

        return JsonObject(mutableMap)
    }
}
