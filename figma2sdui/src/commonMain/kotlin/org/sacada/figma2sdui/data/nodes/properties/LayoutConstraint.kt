package org.sacada.figma2sdui.data.nodes.properties

import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.nodes.enums.HorizontalLayoutConstraint
import org.sacada.figma2sdui.data.nodes.enums.VerticalLayoutConstraint

@Serializable
data class LayoutConstraint(
    val vertical: VerticalLayoutConstraint,
    val horizontal: HorizontalLayoutConstraint,
)
