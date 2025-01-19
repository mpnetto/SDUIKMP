package com.sacada.data.ui.components

import com.sacada.data.ui.components.box.BoxGenerator
import com.sacada.figma2sdui.data.nodes.Instance
import com.sacada.figma2sdui.data.nodes.NodeHelper.Companion.findComponentId
import com.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.putJsonObject

class ComponentHelper {
    companion object {
        fun createActionJson(
            instance: Instance,
            componentDescriptions: Map<String, RootComponentDescription>,
            type: String
        ): JsonObject {
            return BoxGenerator.generateJson(instance, componentDescriptions) { mutableMap ->
                val childrenArray = buildJsonArray {
                    val actionName = getIconName(instance, componentDescriptions)

                    val actionJson = buildJsonObject {
                        put("id", JsonPrimitive(instance.id))
                        put("type", JsonPrimitive("Icon"))
                        putJsonObject("attributes") {
                            put("iconName", JsonPrimitive(actionName))
                        }
                    }

                    add(actionJson)
                }

                mutableMap["children"] = childrenArray
                mutableMap["type"] = JsonPrimitive(type)

                JsonObject(mutableMap)
            }
        }

        private fun getIconName(
            instance: Instance,
            componentDescriptions: Map<String, RootComponentDescription>?
        ): String {
            val componentId = findComponentId(instance)
            return componentDescriptions?.get(componentId)?.name ?: "default_icon"
        }
    }
}
