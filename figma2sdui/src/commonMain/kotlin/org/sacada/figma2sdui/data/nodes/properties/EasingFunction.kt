package org.sacada.figma2sdui.data.nodes.properties

import kotlinx.serialization.Serializable

@Serializable
data class EasingFunction(
    val x1: Double,
    val x2: Double,
    val y1: Double,
    val y2: Double,
)
