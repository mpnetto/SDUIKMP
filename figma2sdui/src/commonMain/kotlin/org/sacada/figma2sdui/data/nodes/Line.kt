package org.sacada.figma2sdui.data.nodes

import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.AdditionalData
import org.sacada.figma2sdui.data.Visitor
import org.sacada.figma2sdui.data.nodes.enums.BlendMode
import org.sacada.figma2sdui.data.nodes.enums.EasingType
import org.sacada.figma2sdui.data.nodes.enums.LayoutAlign
import org.sacada.figma2sdui.data.nodes.enums.StyleType
import org.sacada.figma2sdui.data.nodes.properties.Effect
import org.sacada.figma2sdui.data.nodes.properties.LayoutConstraint
import org.sacada.figma2sdui.data.nodes.properties.Paint
import org.sacada.figma2sdui.data.nodes.properties.Rectangle

@Serializable
data class Line(
    val blendMode: BlendMode,
    val preserveRatio: Boolean,
    val layoutAlign: LayoutAlign,
    val layoutGrow: Double,
    val constraints: LayoutConstraint,
    val transitionNodeID: String,
    val transitionDuration: Int,
    val transitionEasing: EasingType,
    val opacity: Int,
    val absoluteBoundingBox: Rectangle,
    val absoluteRenderBounds: Rectangle,
    val effects: Array<Effect>,
    val size: Vector,
    val isMask: Boolean,
    val fills: Array<Paint>,
    val strokes: Array<Paint>,
    val strokeWeight: Double,
    val styles: Map<StyleType, String>,
) : BaseComponent() {
    override fun <T> accept(
        visitor: Visitor<T>,
        additionalData: AdditionalData?,
    ): T = visitor.visit(this, additionalData)
}
