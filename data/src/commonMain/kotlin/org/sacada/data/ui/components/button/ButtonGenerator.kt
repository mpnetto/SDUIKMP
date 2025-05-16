package org.sacada.data.ui.components.button

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import org.sacada.annotation.RegisterComponent
import org.sacada.data.ui.components.Component
import org.sacada.figma2sdui.data.nodes.Instance
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

@RegisterComponent
object ButtonGenerator : Component.Generator {
    override fun generateJson(
        instance: Instance,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?,
    ): JsonObject {
        val buttonJson =
            buildJsonObject {
                put("id", JsonPrimitive(instance.id))
                put("type", JsonPrimitive("Button"))

                put(
                    "attributes",
                    buildJsonObject {
                        put("width", JsonPrimitive(instance.absoluteBoundingBox.width))
                        put("height", JsonPrimitive(instance.absoluteBoundingBox.height))

                        instance.componentProperties.forEach { (key, value) ->
                            when {
                                key.contains("label", ignoreCase = true) -> {
                                    put("label", value.value)
                                }
                                key.contains("onClick", ignoreCase = true) -> {
                                    put("onClick", value.value)
                                }
                                key.contains("disabled", ignoreCase = true) -> {
                                    put("disabled", value.value)
                                }
                            }
                        }
                    },
                )
            }

        val mutableMap = buttonJson.toMutableMap()

        performAction?.invoke(mutableMap)

        return JsonObject(mutableMap)
    }
}
