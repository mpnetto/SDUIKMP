package org.sacada.figma2sdui.data.nodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.Visitor
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentSet
import org.sacada.figma2sdui.data.nodes.properties.root.RootStyle

@Serializable
data class RootDocument(
    val document: Document,
    @SerialName("components") val componentDescriptions: Map<String, RootComponentDescription>,
    val componentSets: Map<String, RootComponentSet>,
    val schemaVersion: Int,
    val styles: Map<String, RootStyle>,
    val name: String,
    val lastModified: String,
    val version: String,
) : TreeNode() {
    override fun <T> accept(
        visitor: Visitor<T>,
        additionalData: Any?,
    ): T = visitor.visit(this, additionalData)
}
