package org.sacada.figma2sdui.data.nodes.properties

import kotlinx.serialization.Serializable

@Serializable
data class Interaction(
    val trigger: Trigger?,
    val actions: Array<Action?>?,
)
