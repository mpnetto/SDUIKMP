package org.sacada.data.ui.components.textField

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import org.sacada.annotation.RegisterComponent
import org.sacada.data.ui.components.Component
import org.sacada.data.ui.components.ComponentHelper.Companion.createActionJson
import org.sacada.data.ui.components.ComponentHelper.Companion.findComponentByName
import org.sacada.data.util.convertToCamelCase
import org.sacada.data.util.convertToPascalCase
import org.sacada.figma2sdui.data.nodes.BaseComponent
import org.sacada.figma2sdui.data.nodes.Frame
import org.sacada.figma2sdui.data.nodes.Instance
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

@RegisterComponent
object TextFieldGenerator : Component.Generator {
    override fun generateJson(
        baseComponent: BaseComponent,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?,
    ): JsonObject {
        baseComponent as Instance

        val children =
            buildJsonArray {
                componentDescriptions?.let {
                    val stateLayer = findComponentByName(baseComponent, "state-layer")

                    if (stateLayer is Frame) {
                        stateLayer.components.forEach { child ->
                            when {
                                child is Instance ->
                                    add(
                                        createActionJson(
                                            child,
                                            componentDescriptions,
                                            child.name.convertToPascalCase(),
                                        ),
                                    )
                            }
                        }
                    }
                }
            }

        return buildJsonObject {
            put("id", JsonPrimitive(baseComponent.id))
            put("type", JsonPrimitive(baseComponent.componentType.name.convertToCamelCase()))
            put("children", children)
            put(
                "attributes",
                buildJsonObject {
                    put("paddingLeft", JsonPrimitive(baseComponent.paddingLeft))
                    put("paddingRight", JsonPrimitive(baseComponent.paddingRight))
                    put("paddingTop", JsonPrimitive(baseComponent.paddingTop))
                    put("paddingBottom", JsonPrimitive(baseComponent.paddingBottom))
                    put("itemSpacing", JsonPrimitive(baseComponent.itemSpacing))
                    put(
                        "horizontalAlignment",
                        JsonPrimitive(baseComponent.counterAxisAlignItems.name),
                    )
                    put(
                        "verticalArrangement",
                        JsonPrimitive(baseComponent.primaryAxisAlignItems.name ?: ""),
                    )
                    put(
                        "layoutSizingHorizontal",
                        JsonPrimitive(baseComponent.layoutSizingHorizontal.name),
                    )
                    put(
                        "layoutSizingVertical",
                        JsonPrimitive(baseComponent.layoutSizingVertical.name),
                    )

                    baseComponent.componentProperties.forEach { (key, value) ->
                        when {
                            key.contains("placeholder", ignoreCase = true) ->
                                put("placeholder", value.value)

                            key.contains("supporting", ignoreCase = true) ->
                                put("supportingText", value.value)

                            key.contains("label", ignoreCase = true) ->
                                put("label", value.value)

                            key.contains("leading", ignoreCase = true) ->
                                put("showLeadingIcon", value.value)

                            key.contains("trailing", ignoreCase = true) ->
                                put("showTrailingIcon", value.value)

                            key.contains("style", ignoreCase = true) ->
                                put("style", value.value)

                            key.contains("text configurations", ignoreCase = true) ->
                                put("textConfigurations", value.value)
                        }
                    }
                    put(
                        "validation",
                        buildJsonObject {
                            put(
                                "required",
                                baseComponent.componentProperties["required"]?.value
                                    ?: JsonPrimitive(
                                        true,
                                    ),
                            )
                            put(
                                "minLength",
                                baseComponent.componentProperties["minLength"]?.value
                                    ?: JsonPrimitive(5),
                            )
                            put(
                                "regex",
                                baseComponent.componentProperties["regex"]?.value
                                    ?: JsonPrimitive("^[a-zA-Z0-9_]*$"),
                            )
                        },
                    )
                },
            )
        }
    }
}
