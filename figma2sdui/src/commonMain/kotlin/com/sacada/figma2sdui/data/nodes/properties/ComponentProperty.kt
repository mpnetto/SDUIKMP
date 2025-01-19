package com.sacada.figma2sdui.data.nodes.properties

import com.sacada.figma2sdui.data.nodes.enums.ComponentPropertyType
import kotlinx.serialization.Serializable

@Serializable
data class ComponentProperty(
    val type: ComponentPropertyType,
    val value: String
)
