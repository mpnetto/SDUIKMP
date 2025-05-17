package org.sacada.data.ui.components.button

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import org.sacada.annotation.RegisterComponent
import org.sacada.data.ui.components.Component
import org.sacada.figma2sdui.data.nodes.BaseComponent
import org.sacada.figma2sdui.data.nodes.Instance
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

@RegisterComponent
object ButtonGenerator : Component.Generator {
    override fun generateJson(
        baseComponent: BaseComponent,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?,
    ): JsonObject {
        baseComponent as Instance

        val buttonJson =
            buildJsonObject {
                put("id", JsonPrimitive(baseComponent.id))
                put("type", JsonPrimitive("Button"))

                put(
                    "attributes",
                    buildJsonObject {
                        put("width", JsonPrimitive(baseComponent.absoluteBoundingBox.width))
                        put("height", JsonPrimitive(baseComponent.absoluteBoundingBox.height))

                        baseComponent.componentProperties.forEach { (key, value) ->
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
