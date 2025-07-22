package org.sacada.core.blueprint

import org.sacada.core.model.ViewComponent

sealed interface Blueprint {
    val id: String
}

/** Generic blueprint used when no specific blueprint exists. */
data class GenericBlueprint(
    override val id: String,
    val component: ViewComponent
) : Blueprint
