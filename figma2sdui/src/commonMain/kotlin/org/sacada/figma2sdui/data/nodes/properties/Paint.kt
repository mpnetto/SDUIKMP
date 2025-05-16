package org.sacada.figma2sdui.data.nodes.properties

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.nodes.enums.BlendMode
import org.sacada.figma2sdui.data.nodes.enums.PaintType
import org.sacada.figma2sdui.data.nodes.enums.ScaleMode

@Serializable
data class Paint(
    val type: PaintType,
    val visible: Boolean = true,
    val opacity: Int = 100,
    val color: Color,
    val blendMode: BlendMode,
    val gradientHandlePositions: Array<Vector>? = null,
    val gradientStops: Array<ColorStop>? = null,
    val scaleMode: ScaleMode? = null,
    @Contextual
    val rotation: Number? = null,
    val imageRef: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Paint

        if (type != other.type) return false
        if (visible != other.visible) return false
        if (opacity != other.opacity) return false
        if (color != other.color) return false
        if (blendMode != other.blendMode) return false
        if (!gradientHandlePositions.contentEquals(other.gradientHandlePositions)) return false
        if (!gradientStops.contentEquals(other.gradientStops)) return false
        if (scaleMode != other.scaleMode) return false
        if (rotation != other.rotation) return false
        if (imageRef != other.imageRef) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + visible.hashCode()
        result = 31 * result + opacity
        result = 31 * result + color.hashCode()
        result = 31 * result + blendMode.hashCode()
        result = 31 * result + gradientHandlePositions.contentHashCode()
        result = 31 * result + gradientStops.contentHashCode()
        result = 31 * result + scaleMode.hashCode()
        result = 31 * result + rotation.hashCode()
        result = 31 * result + imageRef.hashCode()
        return result
    }
}
