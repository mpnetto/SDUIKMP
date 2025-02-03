package com.sacada.figma2sdui.data.nodes.properties
import kotlinx.serialization.Serializable

@Serializable
data class Overrides(
    val id: String,
    val overridenFields: Array<String> = emptyArray()
)
