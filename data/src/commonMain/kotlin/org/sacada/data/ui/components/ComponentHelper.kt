package org.sacada.data.ui.components

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.putJsonObject
import org.sacada.data.ui.components.box.BoxGenerator
import org.sacada.figma2sdui.data.nodes.BaseComponent
import org.sacada.figma2sdui.data.nodes.Frame
import org.sacada.figma2sdui.data.nodes.Instance
import org.sacada.figma2sdui.data.nodes.NodeHelper.Companion.findComponentId
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

class ComponentHelper {
    companion object {
        fun createActionJson(
            instance: Instance,
            componentDescriptions: Map<String, RootComponentDescription>,
            type: String,
        ): JsonObject =
            BoxGenerator.generateJson(instance, componentDescriptions) { mutableMap ->
                val childrenArray =
                    buildJsonArray {
                        val actionName = getIconName(instance, componentDescriptions)

                        val actionJson =
                            buildJsonObject {
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

        private fun getIconName(
            instance: Instance,
            componentDescriptions: Map<String, RootComponentDescription>?,
        ): String {
            val componentId = findComponentId(instance)
            return componentDescriptions?.get(componentId)?.name ?: "default_icon"
        }

        fun findComponentByName(
            root: BaseComponent,
            name: String,
        ): BaseComponent? {
            if (root.name == name) return root

            val children = (root as? Instance)?.components ?: (root as? Frame)?.components ?: emptyArray()
            for (child in children) {
                val result = findComponentByName(child, name)
                if (result != null) return result
            }
            return null
        }
    }
}
