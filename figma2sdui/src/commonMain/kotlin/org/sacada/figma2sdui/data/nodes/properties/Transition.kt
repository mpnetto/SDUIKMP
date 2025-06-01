package org.sacada.figma2sdui.data.nodes.properties

import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.nodes.enums.TransitionType

@Serializable
data class Transition(
    val type: TransitionType?,
    val easing: Easing?,
)
