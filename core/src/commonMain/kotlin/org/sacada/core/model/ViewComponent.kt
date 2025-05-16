package org.sacada.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ViewComponent(
    val id: String = "",
    val type: String,
    val attributes: Map<String, JsonElement> = emptyMap(),
    val action: Action? = null,
    val children: List<ViewComponent> = emptyList()
)
