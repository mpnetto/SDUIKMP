package org.sacada.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Action(
    val type: String,
    val destination: String? = null
)

fun handleAction(action: Action) {
    when (action.type) {
        "navigate" -> navigateTo(action.destination)
        "event" -> triggerEvent(action.destination)
    }
}

fun navigateTo(destination: String?) {
    println("Navegar para: $destination")
}

fun triggerEvent(event: String?) {
    println("Evento disparado: $event")
}
