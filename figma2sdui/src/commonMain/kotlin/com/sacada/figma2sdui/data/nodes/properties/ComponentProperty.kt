package com.sacada.figma2sdui.data.nodes.properties

import com.sacada.figma2sdui.data.nodes.enums.ComponentPropertyType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ComponentProperty(
    val type: ComponentPropertyType,
    val value: JsonElement
)
