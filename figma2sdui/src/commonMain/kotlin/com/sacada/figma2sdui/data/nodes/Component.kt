package com.sacada.figma2sdui.data.nodes

import com.sacada.figma2sdui.data.AdditionalData
import com.sacada.figma2sdui.data.Visitor
import com.sacada.figma2sdui.data.nodes.enums.AxisSizingMode
import com.sacada.figma2sdui.data.nodes.enums.BlendMode
import com.sacada.figma2sdui.data.nodes.enums.LayoutMode
import com.sacada.figma2sdui.data.nodes.enums.StrokeAlign
import com.sacada.figma2sdui.data.nodes.properties.ComponentPropertyDefinition
import com.sacada.figma2sdui.data.nodes.properties.Effect
import com.sacada.figma2sdui.data.nodes.properties.LayoutConstraint
import com.sacada.figma2sdui.data.nodes.properties.Paint
import com.sacada.figma2sdui.data.nodes.properties.Rectangle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Component(
    val blendMode: BlendMode,
    @SerialName("children") val components: Array<BaseComponent>,
    val absoluteBoundingBox: Rectangle,
    val absoluteRenderBounds: Rectangle,
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

    val componentPropertyDefinitions: Map<String, ComponentPropertyDefinition>
) : BaseComponent() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }


}
