package org.sacada.figma2sdui.data.nodes.properties
import kotlinx.serialization.Serializable

@Serializable
data class Color(
    val r: Double,
    val g: Double,
    val b: Double,
    val a: Double
)
