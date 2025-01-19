package com.sacada.data.ui.components.column

import kotlinx.serialization.json.JsonObject
import com.sacada.annotation.RegisterComponent
import com.sacada.figma2sdui.data.nodes.Instance
import com.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription
import com.sacada.data.ui.components.Component
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject

@RegisterComponent
object ColumnGenerator : Component.Generator {
    override fun generateJson(
        instance: Instance,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?
    ): JsonObject = buildJsonObject {}
}
