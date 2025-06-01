package org.sacada.figma2sdui.data.nodes.properties

import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.nodes.enums.TriggerType

@Serializable
data class Trigger(
    val type: TriggerType,
)
