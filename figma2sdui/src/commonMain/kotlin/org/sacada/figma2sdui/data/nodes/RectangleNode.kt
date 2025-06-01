package org.sacada.figma2sdui.data.nodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.Visitor
import org.sacada.figma2sdui.data.nodes.enums.BlendMode
import org.sacada.figma2sdui.data.nodes.enums.EasingType
import org.sacada.figma2sdui.data.nodes.enums.LayoutAlign
import org.sacada.figma2sdui.data.nodes.properties.Effect
import org.sacada.figma2sdui.data.nodes.properties.LayoutConstraint
import org.sacada.figma2sdui.data.nodes.properties.Paint
import org.sacada.figma2sdui.data.nodes.properties.Rectangle
import org.sacada.figma2sdui.data.nodes.properties.Vector

@Serializable
@SerialName("RECTANGLE")
data class RectangleNode(
    val blendMode: BlendMode = BlendMode.NORMAL,
    val preserveRatio: Boolean = false,
    val layoutAlign: LayoutAlign = LayoutAlign.INHERIT,
    val layoutGrow: Double = 0.0,
    val constraints: LayoutConstraint? = null,
    val transitionNodeID: String = "",
    val transitionDuration: Int = 0,
    val transitionEasing: EasingType = EasingType.EASE_IN_AND_OUT,
    val opacity: Int = 100,
    val absoluteBoundingBox: Rectangle? = null,
    val absoluteRenderBounds: Rectangle? = null,
    val effects: Array<Effect> = emptyArray(),
    val size: Vector = Vector(0.0, 0.0),
    val isMask: Boolean = false,
    val fills: Array<Paint> = emptyArray(),
    val strokes: Array<Paint> = emptyArray(),
    val strokeWeight: Double = 0.0,
    val styles: Map<String, String> = emptyMap(),
    val cornerRadius: Float = 0.0f,
    val rectangleCornerRadii: Array<Float> = emptyArray(),
) : BaseComponent() {
    override fun <T> accept(
        visitor: Visitor<T>,
        additionalData: Any?,
    ): T = visitor.visit(this, additionalData)
}
