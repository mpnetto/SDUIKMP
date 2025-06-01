package org.sacada.figma2sdui.data.nodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.Visitor
import org.sacada.figma2sdui.data.nodes.enums.AxisSizingMode
import org.sacada.figma2sdui.data.nodes.enums.BlendMode
import org.sacada.figma2sdui.data.nodes.enums.LayoutMode
import org.sacada.figma2sdui.data.nodes.enums.StrokeAlign
import org.sacada.figma2sdui.data.nodes.properties.ComponentPropertyDefinition
import org.sacada.figma2sdui.data.nodes.properties.Effect
import org.sacada.figma2sdui.data.nodes.properties.LayoutConstraint
import org.sacada.figma2sdui.data.nodes.properties.Paint
import org.sacada.figma2sdui.data.nodes.properties.Rectangle

@Serializable
data class Component(
    val blendMode: BlendMode,
    @SerialName("children") val components: Array<BaseComponent>,
    val absoluteBoundingBox: Rectangle,
    val absoluteRenderBounds: Rectangle? = null,
    val constraints: LayoutConstraint,
    val clipsContent: Boolean,
    val fills: Array<Paint>,
    val strokes: Array<Paint>,
    val cornerRadius: Double,
    val strokeWeight: Double,
    val strokeAlign: StrokeAlign,
    val styles: Map<String, String>,
    val layoutMode: LayoutMode,
    val itemSpacing: Double,
    val counterAxisAlignItems: AxisSizingMode,
    val primaryAxisAlignItems: AxisSizingMode,
    val paddingLeft: Double,
    val paddingRight: Double,
    val paddingTop: Double,
    val paddingBottom: Double,
    val effects: Array<Effect>,
    val componentPropertyDefinitions: Map<String, ComponentPropertyDefinition>,
) : BaseComponent() {
    override fun <T> accept(
        visitor: Visitor<T>,
        additionalData: Any?,
    ): T = visitor.visit(this, additionalData)
}
