package com.sacada.figma2sdui.data.nodes.properties

import com.sacada.figma2sdui.data.nodes.enums.BlendMode
import com.sacada.figma2sdui.data.nodes.enums.EffectType

import kotlinx.serialization.Serializable

@Serializable
data class Effect(
    val type: EffectType,
    val visible: Boolean,
    val radius: Double,
    val color: Color,
    val blendMode: BlendMode,
    val offset: Vector,
    val spread: Double = 0.0,
    val showShadowBehindNode: Boolean
)
