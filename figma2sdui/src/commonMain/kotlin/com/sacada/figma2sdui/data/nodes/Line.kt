package com.sacada.figma2sdui.data.nodes

import com.sacada.figma2sdui.data.AdditionalData
import com.sacada.figma2sdui.data.Visitor
import com.sacada.figma2sdui.data.nodes.enums.BlendMode
import com.sacada.figma2sdui.data.nodes.enums.EasingType
import com.sacada.figma2sdui.data.nodes.enums.LayoutAlign
import com.sacada.figma2sdui.data.nodes.enums.StyleType
import com.sacada.figma2sdui.data.nodes.properties.Effect
import com.sacada.figma2sdui.data.nodes.properties.LayoutConstraint
import com.sacada.figma2sdui.data.nodes.properties.Paint
import com.sacada.figma2sdui.data.nodes.properties.Rectangle
import com.sacada.figma2sdui.data.nodes.properties.Vector
import kotlinx.serialization.Serializable

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
    val styles: Map<StyleType, String>
) : BaseComponent() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }

}
