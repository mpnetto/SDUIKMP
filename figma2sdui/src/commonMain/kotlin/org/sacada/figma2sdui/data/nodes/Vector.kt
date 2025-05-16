package org.sacada.figma2sdui.data.nodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.AdditionalData
import org.sacada.figma2sdui.data.Visitor
import org.sacada.figma2sdui.data.nodes.enums.BlendMode
import org.sacada.figma2sdui.data.nodes.enums.EasingType
import org.sacada.figma2sdui.data.nodes.enums.LayoutAlign
import org.sacada.figma2sdui.data.nodes.properties.Effect
import org.sacada.figma2sdui.data.nodes.properties.LayoutConstraint
import org.sacada.figma2sdui.data.nodes.properties.Paint
import org.sacada.figma2sdui.data.nodes.properties.Rectangle

@SerialName("VECTOR")
@Serializable
data class Vector(
    val blendMode: BlendMode,
    val preserveRatio: Boolean = false,
    val layoutAlign: LayoutAlign? = null,
    val layoutGrow: Double = 0.0,
    val constraints: LayoutConstraint,
    val transitionNodeID: String? = null,
    val transitionDuration: Int = 0,
    val transitionEasing: EasingType? = null,
    val opacity: Int = 100,
    val absoluteBoundingBox: Rectangle,
    val absoluteRenderBounds: Rectangle,
    val effects: Array<Effect>,
    val size: Vector? = null,
    val isMask: Boolean = false,
    val fills: Array<Paint>,
    val strokes: Array<Paint>,
    val strokeWeight: Double,
    // key is of type StyleType
    val styles: Map<String, String>,
) : BaseComponent() {
    override fun <T> accept(
        visitor: Visitor<T>,
        additionalData: AdditionalData?,
    ): T = visitor.visit(this, additionalData)
}
