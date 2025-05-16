package org.sacada.figma2sdui.data

import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.nodes.NodeType
import org.sacada.figma2sdui.data.nodes.TreeNode

@Serializable
data class AdditionalData(
    val parent: TreeNode,
    val parentType: NodeType,
)
