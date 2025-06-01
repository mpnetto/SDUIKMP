package org.sacada.figma2sdui.data.nodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.Visitor
import org.sacada.figma2sdui.data.nodes.properties.Color
import org.sacada.figma2sdui.data.nodes.properties.FlowStartingPoint
import org.sacada.figma2sdui.data.nodes.properties.PrototypeDevice

@Serializable
data class Page(
    val id: String,
    val name: String,
    val type: NodeType,
    @SerialName("children") val frames: Array<Frame>,
    val backgroundColor: Color,
    val flowStartingPoints: Array<FlowStartingPoint>,
    val prototypeDevice: PrototypeDevice,
) : TreeNode() {
    override fun <T> accept(
        visitor: Visitor<T>,
        additionalData: Any?,
    ): T = visitor.visit(this, additionalData)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Page

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (!frames.contentEquals(other.frames)) return false
        if (backgroundColor != other.backgroundColor) return false
        if (!flowStartingPoints.contentEquals(other.flowStartingPoints)) return false
        if (prototypeDevice != other.prototypeDevice) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + frames.contentHashCode()
        result = 31 * result + backgroundColor.hashCode()
        result = 31 * result + flowStartingPoints.contentHashCode()
        result = 31 * result + prototypeDevice.hashCode()
        return result
    }
}
