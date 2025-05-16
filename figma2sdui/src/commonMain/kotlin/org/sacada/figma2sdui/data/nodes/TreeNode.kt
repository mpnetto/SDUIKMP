package org.sacada.figma2sdui.data.nodes

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.sacada.figma2sdui.analyser.ComponentType
import org.sacada.figma2sdui.data.Visitable

@Serializable
abstract class TreeNode(
    @Transient
    var componentType: ComponentType = ComponentType.UNKNOWN,
) : Visitable
