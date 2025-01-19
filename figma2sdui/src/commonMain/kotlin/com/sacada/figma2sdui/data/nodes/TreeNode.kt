package com.sacada.figma2sdui.data.nodes

import com.sacada.figma2sdui.analyser.ComponentType
import com.sacada.figma2sdui.data.Visitable
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
abstract class TreeNode(
    @Transient
    var componentType: ComponentType = ComponentType.UNKNOWN
) : Visitable
