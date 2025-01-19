package com.sacada.figma2sdui.data.nodes

import com.sacada.figma2sdui.data.AdditionalData
import com.sacada.figma2sdui.data.Visitor
import com.sacada.figma2sdui.data.nodes.enums.BlendMode
import com.sacada.figma2sdui.data.nodes.enums.EasingType
import com.sacada.figma2sdui.data.nodes.enums.LayoutAlign
import com.sacada.figma2sdui.data.nodes.enums.LineType
import com.sacada.figma2sdui.data.nodes.enums.StrokeAlign
import com.sacada.figma2sdui.data.nodes.properties.Effect
import com.sacada.figma2sdui.data.nodes.properties.LayoutConstraint
import com.sacada.figma2sdui.data.nodes.properties.Paint
import com.sacada.figma2sdui.data.nodes.properties.Rectangle
import com.sacada.figma2sdui.data.nodes.properties.TypeStyle
import com.sacada.figma2sdui.data.nodes.properties.Vector
import kotlinx.serialization.Serializable

@Serializable
data class Text(
    val blendMode: BlendMode,
    val preserveRatio: Boolean,
    val layoutAlign: LayoutAlign,
    val layoutGrow: Int,
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
    val strokeWeight: Int,
    val strokeAlign: StrokeAlign,
    val styles: Map<String, String>,

    // specific Text properties
    val characters: String,
    val style: TypeStyle,
    val characterStyleOverrides: Array<Int>,
    val styleOverrideTable: Map<Int, TypeStyle>,
    val lineTypes: Array<LineType>,
    val lineIdentations: Array<Int>
) : BaseComponent() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Text

        if (blendMode != other.blendMode) return false
        if (preserveRatio != other.preserveRatio) return false
        if (layoutAlign != other.layoutAlign) return false
        if (layoutGrow != other.layoutGrow) return false
        if (constraints != other.constraints) return false
        if (transitionNodeID != other.transitionNodeID) return false
        if (transitionDuration != other.transitionDuration) return false
        if (transitionEasing != other.transitionEasing) return false
        if (opacity != other.opacity) return false
        if (absoluteBoundingBox != other.absoluteBoundingBox) return false
        if (absoluteRenderBounds != other.absoluteRenderBounds) return false
        if (!effects.contentEquals(other.effects)) return false
        if (size != other.size) return false
        if (isMask != other.isMask) return false
        if (!fills.contentEquals(other.fills)) return false
        if (!strokes.contentEquals(other.strokes)) return false
        if (strokeWeight != other.strokeWeight) return false
        if (strokeAlign != other.strokeAlign) return false
        if (styles != other.styles) return false
        if (characters != other.characters) return false
        if (style != other.style) return false
        if (!characterStyleOverrides.contentEquals(other.characterStyleOverrides)) return false
        if (styleOverrideTable != other.styleOverrideTable) return false
        if (!lineTypes.contentEquals(other.lineTypes)) return false
        if (!lineIdentations.contentEquals(other.lineIdentations)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = blendMode.hashCode()
        result = 31 * result + preserveRatio.hashCode()
        result = 31 * result + layoutAlign.hashCode()
        result = 31 * result + layoutGrow
        result = 31 * result + constraints.hashCode()
        result = 31 * result + transitionNodeID.hashCode()
        result = 31 * result + transitionDuration
        result = 31 * result + transitionEasing.hashCode()
        result = 31 * result + opacity
        result = 31 * result + absoluteBoundingBox.hashCode()
        result = 31 * result + absoluteRenderBounds.hashCode()
        result = 31 * result + effects.contentHashCode()
        result = 31 * result + size.hashCode()
        result = 31 * result + isMask.hashCode()
        result = 31 * result + fills.contentHashCode()
        result = 31 * result + strokes.contentHashCode()
        result = 31 * result + strokeWeight
        result = 31 * result + strokeAlign.hashCode()
        result = 31 * result + styles.hashCode()
        result = 31 * result + characters.hashCode()
        result = 31 * result + style.hashCode()
        result = 31 * result + characterStyleOverrides.contentHashCode()
        result = 31 * result + styleOverrideTable.hashCode()
        result = 31 * result + lineTypes.contentHashCode()
        result = 31 * result + lineIdentations.contentHashCode()
        return result
    }

}
