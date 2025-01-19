package com.sacada.figma2sdui.data.nodes.properties

import com.sacada.figma2sdui.data.nodes.enums.BlendMode
import com.sacada.figma2sdui.data.nodes.enums.EffectType

import kotlinx.serialization.Serializable

@Serializable
data class Effect(
    val type: EffectType,
    val visible: Boolean,
    val radius: Int,
    val color: Color,
    val blendMode: BlendMode,
    val offset: Vector,
    val spread: Int,
    val showShadowBehindNode: Boolean
)
