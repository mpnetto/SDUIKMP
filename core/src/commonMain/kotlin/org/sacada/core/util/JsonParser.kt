package org.sacada.core.util

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.sacada.core.model.ViewScreens

object JsonParser {
    fun parseScreens(json: String): ViewScreens? =
        try {
            Json.decodeFromString(ViewScreens.serializer(), json)
        } catch (e: SerializationException) {
            println("Error deserializing JSON: ${e.message}")
            null
        }
}
