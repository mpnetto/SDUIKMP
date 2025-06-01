package org.sacada.figma2sdui.data.nodes.properties

import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.nodes.enums.EasingType

@Serializable
data class Easing(
    val type: EasingType?,
    val easingFunctionCubicBezier: EasingFunction?,
)
