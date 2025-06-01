package org.sacada.data.ui.components.bottomBar

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import org.sacada.annotation.RegisterComponent
import org.sacada.data.ui.components.Component
import org.sacada.data.ui.components.ComponentHelper.Companion.createActionJson
import org.sacada.data.util.convertToCamelCase
import org.sacada.figma2sdui.data.NavigationData
import org.sacada.figma2sdui.data.nodes.BaseComponent
import org.sacada.figma2sdui.data.nodes.Frame
import org.sacada.figma2sdui.data.nodes.Instance
import org.sacada.figma2sdui.data.nodes.enums.NavigationType
import org.sacada.figma2sdui.data.nodes.enums.TriggerType
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

@RegisterComponent
object BottomBarGenerator : Component.Generator {
    override fun generateJson(
        baseComponent: BaseComponent,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?,
    ): JsonObject {
        baseComponent as Instance

        val bottomAppBarJson =
            buildJsonObject {
                put("id", JsonPrimitive(baseComponent.id))
                put("type", JsonPrimitive(baseComponent.componentType.name.convertToCamelCase()))
                put(
                    "children",
                    buildJsonArray {
                        baseComponent.components.forEach { child ->
                            (child as? Frame)?.components?.forEach { component ->

                                val navigationAction =
                                    child.interactions
                                        .firstOrNull { interaction -> interaction.trigger?.type == TriggerType.ON_CLICK }
                                        ?.let { interaction ->
                                            interaction.actions
                                                ?.firstOrNull { it?.navigation == NavigationType.NAVIGATE }
                                                ?.let { action ->
                                                    NavigationData(
                                                        destinationId = action.destinationId,
                                                        navigation = action.navigation,
                                                        transition = action.transition,
                                                    )
                                                }
                                        }

                                component as Instance
                                add(
                                    if (component.name == "FAB") {
                                        createActionJson(
                                            component,
                                            componentDescriptions!!,
                                            "FloatingActionButton",
                                            navigationAction,
                                        )
                                    } else {
                                        createActionJson(
                                            component,
                                            componentDescriptions!!,
                                            "Action",
                                        )
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
