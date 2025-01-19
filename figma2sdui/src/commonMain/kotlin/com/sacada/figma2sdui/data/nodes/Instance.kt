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
data class Instance(
    val blendMode: BlendMode,
    @SerialName("children") val components: Array<BaseComponent>,
    val absoluteBoundingBox: Rectangle,
    val absoluteRenderBounds: Rectangle,
    val constraints: LayoutConstraint,
    val clipsContent: Boolean,
    val fills: Array<Paint>,
    val strokes: Array<Paint>,
    val cornerRadius: Int,
    val strokeWeight: Int,
    val strokeAlign: StrokeAlign,
    val styles: Map<String, String>,
    val layoutMode: LayoutMode,
    val itemSpacing: Int,
    val counterAxisAlignItems: AxisSizingMode,
    val primaryAxisAlignItems: AxisSizingMode,
    val paddingLeft: Int,
    val paddingRight: Int,
    val paddingTop: Int,
    val paddingBottom: Int,
    val effects: Array<Effect>,
    val componentId: String,
    val isExposedInstance: Boolean,
    val exposedInstances: Array<String>,
    val componentProperties: Map<String, ComponentProperty>,
    val overrides: Array<Overrides>
) : BaseComponent() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Instance

        if (blendMode != other.blendMode) return false
        if (!components.contentEquals(other.components)) return false
        if (absoluteBoundingBox != other.absoluteBoundingBox) return false
        if (absoluteRenderBounds != other.absoluteRenderBounds) return false
        if (constraints != other.constraints) return false
        if (clipsContent != other.clipsContent) return false
        if (!fills.contentEquals(other.fills)) return false
        if (!strokes.contentEquals(other.strokes)) return false
        if (cornerRadius != other.cornerRadius) return false
        if (strokeWeight != other.strokeWeight) return false
        if (strokeAlign != other.strokeAlign) return false
        if (styles != other.styles) return false
        if (layoutMode != other.layoutMode) return false
        if (itemSpacing != other.itemSpacing) return false
        if (counterAxisAlignItems != other.counterAxisAlignItems) return false
        if (primaryAxisAlignItems != other.primaryAxisAlignItems) return false
        if (paddingLeft != other.paddingLeft) return false
        if (paddingRight != other.paddingRight) return false
        if (paddingTop != other.paddingTop) return false
        if (paddingBottom != other.paddingBottom) return false
        if (!effects.contentEquals(other.effects)) return false
        if (componentId != other.componentId) return false
        if (isExposedInstance != other.isExposedInstance) return false
        if (!exposedInstances.contentEquals(other.exposedInstances)) return false
        if (componentProperties != other.componentProperties) return false
        if (!overrides.contentEquals(other.overrides)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = blendMode.hashCode()
        result = 31 * result + components.contentHashCode()
        result = 31 * result + absoluteBoundingBox.hashCode()
        result = 31 * result + absoluteRenderBounds.hashCode()
        result = 31 * result + constraints.hashCode()
        result = 31 * result + clipsContent.hashCode()
        result = 31 * result + fills.contentHashCode()
        result = 31 * result + strokes.contentHashCode()
        result = 31 * result + cornerRadius
        result = 31 * result + strokeWeight
        result = 31 * result + strokeAlign.hashCode()
        result = 31 * result + styles.hashCode()
        result = 31 * result + layoutMode.hashCode()
        result = 31 * result + itemSpacing
        result = 31 * result + counterAxisAlignItems.hashCode()
        result = 31 * result + primaryAxisAlignItems.hashCode()
        result = 31 * result + paddingLeft
        result = 31 * result + paddingRight
        result = 31 * result + paddingTop
        result = 31 * result + paddingBottom
        result = 31 * result + effects.contentHashCode()
        result = 31 * result + componentId.hashCode()
        result = 31 * result + isExposedInstance.hashCode()
        result = 31 * result + exposedInstances.contentHashCode()
        result = 31 * result + componentProperties.hashCode()
        result = 31 * result + overrides.contentHashCode()
        return result
    }

}
