package com.sacada.jsonbuilder

import com.sacada.figma2sdui.fetchFigmaData
import com.sacada.jsonbuilder.converter.FigmaJsonConverter
import kotlinx.serialization.json.JsonObject

fun convertFigmaData(apiKey: String, fileKey: String, onResult: (JsonObject?) -> Unit) {
    fetchFigmaData(apiKey, fileKey) { rootDocument ->
        val converter = FigmaJsonConverter()

        converter.convert(rootDocument!!)

        onResult(converter.convert(rootDocument))
    }
}
