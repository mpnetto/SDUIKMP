package org.sacada.jsonbuilder

import kotlinx.serialization.json.JsonObject
import org.sacada.figma2sdui.fetchFigmaData
import org.sacada.jsonbuilder.converter.FigmaJsonConverter

fun convertFigmaData(
    apiKey: String,
    fileKey: String,
    onResult: (JsonObject?) -> Unit,
) {
    fetchFigmaData(apiKey, fileKey) { rootDocument ->
        val converter = FigmaJsonConverter()

        converter.convert(rootDocument!!)

        onResult(converter.convert(rootDocument))
    }
}
