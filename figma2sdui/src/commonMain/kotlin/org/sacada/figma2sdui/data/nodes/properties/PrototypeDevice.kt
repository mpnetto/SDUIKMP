package org.sacada.figma2sdui.data.nodes.properties

import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.nodes.enums.PrototypeDeviceType
import org.sacada.figma2sdui.data.nodes.enums.RotationType

@Serializable
data class PrototypeDevice(
    val type: PrototypeDeviceType,
    val size: Size,
    val presetIdentifier: String,
    val rotation: RotationType,
)
