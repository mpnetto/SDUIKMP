package org.sacada.figma2sdui.data.nodes.properties.root

import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.nodes.enums.StyleType

@Serializable
data class RootStyle(
    val key: String,
    val name: String,
    val styleType: StyleType,
    val remote: Boolean,
    val description: String,
)
