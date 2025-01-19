package com.sacada.figma2sdui.data

import com.sacada.figma2sdui.data.nodes.NodeType
import com.sacada.figma2sdui.data.nodes.TreeNode
import kotlinx.serialization.Serializable

@Serializable
data class AdditionalData(
    val parent: TreeNode,
    val parentType: NodeType
)
