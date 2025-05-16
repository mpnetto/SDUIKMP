package org.sacada.figma2sdui.data.nodes.properties

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import org.sacada.figma2sdui.data.nodes.enums.ComponentPropertyType

@Serializable
data class ComponentProperty(
    val type: ComponentPropertyType,
    val value: JsonElement,
)
