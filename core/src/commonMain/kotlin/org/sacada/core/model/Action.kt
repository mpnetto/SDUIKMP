package org.sacada.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Action(
    val type: String,
    val destination: String? = null,
)
