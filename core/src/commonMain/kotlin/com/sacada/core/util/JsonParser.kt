package com.sacada.core.util

import com.sacada.core.model.ViewScreens
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

object JsonParser {
    fun parseScreens(json: String): ViewScreens? {
        return try {
            Json.decodeFromString(ViewScreens.serializer(), json)
        } catch (e: SerializationException) {
            println("Error deserializing JSON: ${e.message}")
            null
        }
    }
}
