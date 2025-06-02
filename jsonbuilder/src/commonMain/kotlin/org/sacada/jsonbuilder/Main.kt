package org.sacada.jsonbuilder

import kotlinx.serialization.json.JsonObject
import org.sacada.figma2sdui.fetchFigmaData
import org.sacada.jsonbuilder.converter.FigmaJsonConverter

suspend fun convertFigmaData(
    apiKey: String,
    fileKey: String,
): JsonObject? {
    val rootDocument =
        fetchFigmaData(apiKey, fileKey)

    return rootDocument?.let {
        val converter = FigmaJsonConverter()
        converter.convert(it)
    }
}
