package com.sacada.data.ui.components.checkbox

import com.sacada.annotation.RegisterComponent
import com.sacada.data.ui.components.Component
import com.sacada.data.util.convertToCamelCase
import com.sacada.figma2sdui.data.nodes.Instance
import com.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

@RegisterComponent
object CheckboxGenerator : Component.Generator {
    override fun generateJson(
        instance: Instance,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?
    ): JsonObject {
        return buildJsonObject {
            put("id", JsonPrimitive(instance.id))
            put("type", JsonPrimitive(instance.componentType.name.convertToCamelCase()))
            put("attributes", buildJsonObject {
                instance.componentProperties.forEach { (key, value) ->
                    when {
                        key.contains("label", ignoreCase = true) -> put("label", JsonPrimitive(value.value))
                        key.contains("checked", ignoreCase = true) -> put("checked", JsonPrimitive(value.value.toBoolean()))
                        key.contains("disabled", ignoreCase = true) -> put("disabled", JsonPrimitive(value.value.toBoolean()))
                    }
                }
            })
        }
    }
}
