package com.sacada.figma2sdui.data.nodes

import com.sacada.figma2sdui.data.AdditionalData
import com.sacada.figma2sdui.data.Visitor
import com.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription
import com.sacada.figma2sdui.data.nodes.properties.root.RootComponentSet
import com.sacada.figma2sdui.data.nodes.properties.root.RootStyle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RootDocument(
    val document: Document,
    @SerialName("components") val componentDescriptions: Map<String, RootComponentDescription>,
    val componentSets: Map<String, RootComponentSet>,
    val schemaVersion: Int,
    val styles: Map<String, RootStyle>,
    val name: String,
    val lastModified: String,
    val version: String

) : TreeNode() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }
}
