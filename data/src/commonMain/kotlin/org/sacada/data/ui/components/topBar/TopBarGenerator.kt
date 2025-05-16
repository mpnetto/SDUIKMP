package org.sacada.data.ui.components.topBar

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import org.sacada.annotation.RegisterComponent
import org.sacada.data.ui.components.Component
import org.sacada.data.ui.components.ComponentHelper.Companion.createActionJson
import org.sacada.data.util.convertToCamelCase
import org.sacada.figma2sdui.data.nodes.Frame
import org.sacada.figma2sdui.data.nodes.Instance
import org.sacada.figma2sdui.data.nodes.Text
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

@RegisterComponent
object TopBarGenerator : Component.Generator {
    override fun generateJson(
        instance: Instance,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?,
    ): JsonObject {
        val children =
            buildJsonArray {
                componentDescriptions?.let {
                    instance.components.forEachIndexed { index, child ->
                        when {
                            index == 0 && child is Instance ->
                                add(
                                    createActionJson(child, componentDescriptions, "navigationIcon"),
                                )
                            child is Instance ->
                                add(
                                    createActionJson(child, componentDescriptions, "Action"),
                                )
                            child is Frame -> {
                                child.components.filterIsInstance<Instance>().forEach { sub ->
                                    add(createActionJson(sub, componentDescriptions, "Action"))
                                }
                            }
                        }
                    }
                }
            }
        return buildJsonObject {
            put("id", JsonPrimitive(instance.id))
            put("type", JsonPrimitive(instance.componentType.name.convertToCamelCase()))
            put("children", children)
            put(
                "attributes",
                buildJsonObject {
                    put("paddingLeft", JsonPrimitive(instance.paddingLeft))
                    put("paddingRight", JsonPrimitive(instance.paddingRight))
                    put("paddingTop", JsonPrimitive(instance.paddingTop))
                    put("paddingBottom", JsonPrimitive(instance.paddingBottom))
                    put("scrollBehavior", JsonPrimitive("pinned"))
                    put("topBarType", instance.componentProperties["Configuration"]?.value ?: JsonPrimitive("default"))
                    (instance.components.find { it is Text } as? Text)?.let {
                        put("title", JsonPrimitive(it.characters))
                    }
                },
            )
        }
    }
}
