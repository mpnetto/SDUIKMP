package com.sacada.figma2sdui.data.nodes.properties

import com.sacada.figma2sdui.data.nodes.enums.HorizontalLayoutConstraint
import com.sacada.figma2sdui.data.nodes.enums.VerticalLayoutConstraint
import kotlinx.serialization.Serializable

@Serializable
data class LayoutConstraint(
    val vertical: VerticalLayoutConstraint,
    val horizontal: HorizontalLayoutConstraint
)
