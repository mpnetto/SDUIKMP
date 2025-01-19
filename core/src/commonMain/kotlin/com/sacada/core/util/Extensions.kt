package com.sacada.core.util

import com.sacada.core.model.ViewComponent
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

fun ViewComponent.getStringAttribute(key: String): String {
    return attributes[key]?.let {
        if (it is JsonPrimitive) {
            it.contentOrNull
        } else {
            it.toString()
        }
    } ?: ""
}

fun ViewComponent.getBooleanAttribute(key: String): Boolean {
    return attributes[key]?.let {
        if (it is JsonPrimitive) {
            it.booleanOrNull
        } else {
            it.toString().toBoolean()
        }
    } ?: false
}

fun ViewComponent.getSubAttributes(key: String): Map<String, JsonElement>? {
    return attributes[key]?.jsonObject?.toMap()
}

fun ViewComponent.isValid(value: String): Boolean {
    val validationAttributes = this.getSubAttributes("validation") ?: return true

    val isRequired = validationAttributes["required"]?.jsonPrimitive?.booleanOrNull ?: false
    if (isRequired && value.isBlank()) return false

    val minLength = validationAttributes["minLength"]?.jsonPrimitive?.intOrNull
    if (minLength != null && value.length < minLength) return false

    val regex = validationAttributes["regex"]?.jsonPrimitive?.contentOrNull
    if (regex != null && !Regex(regex).matches(value)) return false

    return true
}
