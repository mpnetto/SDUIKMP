package org.sacada.data.ui.components.column

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import org.sacada.annotation.RegisterComponent
import org.sacada.data.ui.components.Component
import org.sacada.figma2sdui.data.nodes.BaseComponent
import org.sacada.figma2sdui.data.nodes.Frame
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

@RegisterComponent
object ColumnGenerator : Component.Generator {
    override fun generateJson(
        baseComponent: BaseComponent,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?,
    ): JsonObject {
        val columnJson =

            buildJsonObject {
                baseComponent as Frame

                put("id", JsonPrimitive(baseComponent.id))
                put("type", JsonPrimitive("Column"))
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
                    },
                )
            }

        val mutableMap = columnJson.toMutableMap()

        performAction?.invoke(mutableMap)

        return JsonObject(mutableMap)
    }
}
