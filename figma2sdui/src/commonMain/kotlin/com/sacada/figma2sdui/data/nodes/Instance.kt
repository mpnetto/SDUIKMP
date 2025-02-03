package com.sacada.figma2sdui.data.nodes

import com.sacada.figma2sdui.data.AdditionalData
import com.sacada.figma2sdui.data.Visitor
import com.sacada.figma2sdui.data.nodes.enums.AxisSizingMode
import com.sacada.figma2sdui.data.nodes.enums.BlendMode
import com.sacada.figma2sdui.data.nodes.enums.LayoutMode
import com.sacada.figma2sdui.data.nodes.enums.StrokeAlign
import com.sacada.figma2sdui.data.nodes.properties.ComponentProperty
import com.sacada.figma2sdui.data.nodes.properties.Effect
import com.sacada.figma2sdui.data.nodes.properties.LayoutConstraint
import com.sacada.figma2sdui.data.nodes.properties.Overrides
import com.sacada.figma2sdui.data.nodes.properties.Paint
import com.sacada.figma2sdui.data.nodes.properties.Rectangle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("INSTANCE")
data class Instance(
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
    val paddingLeft: Double = 0.0,
    val paddingRight: Double = 0.0,
    val paddingTop: Double = 0.0,
    val paddingBottom: Double = 0.0,
    val effects: Array<Effect> = emptyArray(),
    val componentId: String = "",
    val isExposedInstance: Boolean = false,
    val exposedInstances: Array<String> = emptyArray(),
    val componentProperties: Map<String, ComponentProperty> = emptyMap(),
    val overrides: Array<Overrides> = emptyArray()
) : BaseComponent() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }

    override fun resolveComponentId(): String = componentId
    override fun resolveComponents(): Array<BaseComponent> = components

}
