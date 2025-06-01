package org.sacada.figma2sdui.data.nodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.Visitor
import org.sacada.figma2sdui.data.nodes.enums.AxisSizingMode
import org.sacada.figma2sdui.data.nodes.enums.BlendMode
import org.sacada.figma2sdui.data.nodes.enums.LayoutMode
import org.sacada.figma2sdui.data.nodes.enums.LayoutSizingMode
import org.sacada.figma2sdui.data.nodes.enums.StrokeAlign
import org.sacada.figma2sdui.data.nodes.properties.Effect
import org.sacada.figma2sdui.data.nodes.properties.Interaction
import org.sacada.figma2sdui.data.nodes.properties.LayoutConstraint
import org.sacada.figma2sdui.data.nodes.properties.Paint
import org.sacada.figma2sdui.data.nodes.properties.Rectangle

@SerialName("FRAME")
@Serializable
data class Frame(
    val blendMode: BlendMode,
    @SerialName("children") val components: Array<BaseComponent> = emptyArray(),
    val absoluteBoundingBox: Rectangle,
    val absoluteRenderBounds: Rectangle? = null,
    val constraints: LayoutConstraint? = null,
    val clipsContent: Boolean = false,
    val fills: Array<Paint> = emptyArray(),
    val strokes: Array<Paint> = emptyArray(),
    val cornerRadius: Double = 0.0,
    val strokeWeight: Double = 0.0,
    val strokeAlign: StrokeAlign = StrokeAlign.CENTER,
    val styles: Map<String, String> = emptyMap(),
    val layoutMode: LayoutMode = LayoutMode.NONE,
    val itemSpacing: Double = 0.0,
    val counterAxisAlignItems: AxisSizingMode = AxisSizingMode.CENTER,
    val primaryAxisAlignItems: AxisSizingMode = AxisSizingMode.CENTER,
    val layoutSizingHorizontal: LayoutSizingMode = LayoutSizingMode.FIXED,
    val layoutSizingVertical: LayoutSizingMode = LayoutSizingMode.FIXED,
    val paddingLeft: Double = 0.0,
    val paddingRight: Double = 0.0,
    val paddingTop: Double = 0.0,
    val paddingBottom: Double = 0.0,
    val interactions: Array<Interaction> = emptyArray(),
    val effects: Array<Effect> = emptyArray(),
) : BaseComponent() {
    override fun <T> accept(
        visitor: Visitor<T>,
        additionalData: Any?,
    ): T = visitor.visit(this, additionalData)

    override fun resolveComponents(): Array<BaseComponent> = components
}
