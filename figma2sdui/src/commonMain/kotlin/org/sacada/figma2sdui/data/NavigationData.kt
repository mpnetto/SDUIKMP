package org.sacada.figma2sdui.data

import org.sacada.figma2sdui.data.nodes.enums.NavigationType
import org.sacada.figma2sdui.data.nodes.properties.Transition

data class NavigationData(
    val destinationId: String? = null,
    val navigation: NavigationType? = null,
    val transition: Transition? = null,
)
