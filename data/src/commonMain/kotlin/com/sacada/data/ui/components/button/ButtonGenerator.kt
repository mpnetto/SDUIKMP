package com.sacada.data.ui.components.button

import com.sacada.annotation.RegisterComponent
import com.sacada.data.ui.components.Component
import com.sacada.figma2sdui.data.nodes.Instance
import com.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

@RegisterComponent
object ButtonGenerator : Component.Generator {

    override fun generateJson(
        instance: Instance,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?
    ): JsonObject {

        val buttonJson = buildJsonObject {
            put("id", JsonPrimitive(instance.id))
            put("type", JsonPrimitive("Button"))

            put("attributes", buildJsonObject {
                put("width", JsonPrimitive(instance.absoluteBoundingBox.width))
                put("height", JsonPrimitive(instance.absoluteBoundingBox.height))

                instance.componentProperties.forEach { (key, value) ->
                    when {
                        key.contains("label", ignoreCase = true) -> {
                            put("label", JsonPrimitive(value.value))
                        }
                        key.contains("onClick", ignoreCase = true) -> {
                            put("onClick", JsonPrimitive(value.value))
                        }
                        key.contains("disabled", ignoreCase = true) -> {
                            put("disabled", JsonPrimitive(value.value.toBoolean()))
                        }
                    }
                }
            })
        }

        val mutableMap = buttonJson.toMutableMap()

        performAction?.invoke(mutableMap)

        return JsonObject(mutableMap)
    }
}