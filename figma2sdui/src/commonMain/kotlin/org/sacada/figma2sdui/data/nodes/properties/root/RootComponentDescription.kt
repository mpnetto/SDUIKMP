package org.sacada.figma2sdui.data.nodes.properties.root

import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.nodes.properties.DocumentationLink

@Serializable
data class RootComponentDescription(
    val key: String,
    val name: String,
    val description: String,
    val remote: Boolean,
    val documentationLinks: Array<DocumentationLink>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RootComponentDescription

        if (key != other.key) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (remote != other.remote) return false
        if (!documentationLinks.contentEquals(other.documentationLinks)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + remote.hashCode()
        result = 31 * result + documentationLinks.contentHashCode()
        return result
    }
}
