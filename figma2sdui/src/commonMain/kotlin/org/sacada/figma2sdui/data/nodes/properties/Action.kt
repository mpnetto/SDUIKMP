package org.sacada.figma2sdui.data.nodes.properties

import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.nodes.enums.ActionType
import org.sacada.figma2sdui.data.nodes.enums.NavigationType

@Serializable
data class Action(
    val type: ActionType? = null,
    val destinationId: String? = null,
    val navigation: NavigationType? = null,
    val transition: Transition? = null,
)
