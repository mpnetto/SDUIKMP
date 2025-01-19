package com.sacada.figma2sdui.data.nodes.properties

import com.sacada.figma2sdui.data.nodes.enums.PrototypeDeviceType
import com.sacada.figma2sdui.data.nodes.enums.RotationType
import kotlinx.serialization.Serializable

@Serializable
data class PrototypeDevice(
    val type: PrototypeDeviceType,
    val size: Size,
    val presetIdentifier: String,
    val rotation: RotationType
)
