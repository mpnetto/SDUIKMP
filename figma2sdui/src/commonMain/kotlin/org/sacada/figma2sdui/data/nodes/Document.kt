package org.sacada.figma2sdui.data.nodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.AdditionalData
import org.sacada.figma2sdui.data.Visitor

@Serializable
data class Document(
    val id: String,
    val name: String,
    val type: NodeType,
    @SerialName("children") val pages: Array<Page>,
) : TreeNode() {
    override fun <T> accept(
        visitor: Visitor<T>,
        additionalData: AdditionalData?,
    ): T = visitor.visit(this, additionalData)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Document

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (!pages.contentEquals(other.pages)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + pages.contentHashCode()
        return result
    }
}
