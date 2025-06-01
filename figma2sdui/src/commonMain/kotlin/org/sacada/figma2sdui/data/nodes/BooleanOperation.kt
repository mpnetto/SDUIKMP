package org.sacada.figma2sdui.data.nodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.Visitor
import org.sacada.figma2sdui.data.nodes.enums.BlendMode
import org.sacada.figma2sdui.data.nodes.enums.BooleanOperationType
import org.sacada.figma2sdui.data.nodes.enums.EasingType
import org.sacada.figma2sdui.data.nodes.enums.LayoutAlign
import org.sacada.figma2sdui.data.nodes.properties.Effect
import org.sacada.figma2sdui.data.nodes.properties.LayoutConstraint
import org.sacada.figma2sdui.data.nodes.properties.Paint
import org.sacada.figma2sdui.data.nodes.properties.Rectangle

@Serializable
class BooleanOperation(
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
    val styles: Map<String, String>,
    @SerialName("children") val components: Array<BaseComponent>,
    val booleanOperation: BooleanOperationType,
) : BaseComponent() {
    override fun <T> accept(
        visitor: Visitor<T>,
        additionalData: Any?,
    ): T = visitor.visit(this, additionalData)
}
