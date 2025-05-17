package org.sacada.data.ui.components.text

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import org.sacada.annotation.RegisterComponent
import org.sacada.data.ui.components.Component
import org.sacada.figma2sdui.data.nodes.BaseComponent
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

@RegisterComponent
object TextGenerator : Component.Generator {
    override fun generateJson(
        baseComponent: BaseComponent,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?,
    ): JsonObject = buildJsonObject {}
}
