package org.sacada.figma2sdui.data.nodes.properties

import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.nodes.enums.BlendMode
import org.sacada.figma2sdui.data.nodes.enums.EffectType

@Serializable
data class Effect(
    val type: EffectType,
    val visible: Boolean,
    val radius: Double,
    val color: Color,
    val blendMode: BlendMode,
    val offset: Vector,
    val spread: Double = 0.0,
    val showShadowBehindNode: Boolean,
)
