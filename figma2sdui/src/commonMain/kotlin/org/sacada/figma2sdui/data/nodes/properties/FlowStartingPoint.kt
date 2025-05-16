package org.sacada.figma2sdui.data.nodes.properties
import kotlinx.serialization.Serializable

@Serializable
data class FlowStartingPoint(
    val nodeId: String,
    val name: String
)
