package com.sacada.figma2sdui.data.nodes.properties.root

import com.sacada.figma2sdui.data.nodes.enums.StyleType
import kotlinx.serialization.Serializable

@Serializable
data class RootStyle(
    val key: String,
    val name: String,
    val styleType: StyleType,
    val remote: Boolean,
    val description: String
)
