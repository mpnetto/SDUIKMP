package com.sacada.data.ui.components.bottomBar

import com.sacada.annotation.RegisterComponent
import com.sacada.data.ui.components.Component
import com.sacada.data.ui.components.ComponentHelper.Companion.createActionJson
import com.sacada.data.util.convertToCamelCase
import com.sacada.figma2sdui.data.nodes.Frame
import com.sacada.figma2sdui.data.nodes.Instance
import com.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject

@RegisterComponent
object BottomBarGenerator : Component.Generator {
    override fun generateJson(
        instance: Instance,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?,
    ): JsonObject {
        val bottomAppBarJson =
            buildJsonObject {
                put("id", JsonPrimitive(instance.id))
                put("type", JsonPrimitive(instance.componentType.name.convertToCamelCase()))
                put(
                    "children",
                    buildJsonArray {
                        instance.components.forEach { child ->
                            (child as? Frame)?.components?.forEach { component ->
                                component as Instance
                                add(
                                    if (component.name == "FAB") {
                                        createActionJson(component, componentDescriptions!!, "FloatingActionButton")
                                    } else {
                                        createActionJson(component, componentDescriptions!!, "Action")
                                    },
                                )
                            }
                        }
                    },
                )
            }

        val mutableMap = bottomAppBarJson.toMutableMap()

        return JsonObject(mutableMap)
    }
}
