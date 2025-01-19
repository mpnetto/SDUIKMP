package com.sacada.figma2sdui.data.nodes

import kotlinx.serialization.Serializable

@Serializable
abstract class BaseComponent(
    val id: String = "",
    var name: String = "",
    val type: NodeType = NodeType.UNKNOWN

) : TreeNode()
