package org.sacada.figma2sdui.data.nodes

import kotlinx.serialization.Serializable

@Serializable
sealed class BaseComponent(
    val id: String = "",
    var name: String = "",
    val type: NodeType = NodeType.UNKNOWN

) : TreeNode() {
    open fun resolveComponentId(): String? = null
    open fun resolveComponents(): Array<BaseComponent>? = null
}
