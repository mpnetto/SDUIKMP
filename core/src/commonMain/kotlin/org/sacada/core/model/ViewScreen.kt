package org.sacada.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ViewScreen(
    val id: String? = null,
    val name: String? = null,
    val type: String? = null,
    val attributes: Map<String, JsonElement> = emptyMap(),
    val topBar: ViewComponent? = null,
    val bottomBar: ViewComponent? = null,
    val layout: ViewComponent? = null,
)

@Serializable
data class ViewScreens(
    val screens: List<ViewScreen>,
)
