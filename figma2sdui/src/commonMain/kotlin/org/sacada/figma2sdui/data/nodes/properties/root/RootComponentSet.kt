package org.sacada.figma2sdui.data.nodes.properties.root
import kotlinx.serialization.Serializable

@Serializable
data class RootComponentSet(
    val key: String,
    val name: String,
    val description: String,
    val remote: Boolean
)
