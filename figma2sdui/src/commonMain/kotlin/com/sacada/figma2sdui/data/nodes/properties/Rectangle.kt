package com.sacada.figma2sdui.data.nodes.properties
import kotlinx.serialization.Serializable

@Serializable
data class Rectangle(
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double
)
