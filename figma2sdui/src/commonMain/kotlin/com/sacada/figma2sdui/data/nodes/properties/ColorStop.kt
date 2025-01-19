package com.sacada.figma2sdui.data.nodes.properties
import kotlinx.serialization.Serializable

@Serializable
data class ColorStop(
    val position: Float,
    val color: Color
)
