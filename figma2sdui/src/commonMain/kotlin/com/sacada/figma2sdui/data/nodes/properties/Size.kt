package com.sacada.figma2sdui.data.nodes.properties
import kotlinx.serialization.Serializable

@Serializable
data class Size(
    val width: Int,
    val height: Int
)
