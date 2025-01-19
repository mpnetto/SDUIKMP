package com.sacada.jsonbuilder.converter

import kotlinx.serialization.json.JsonObject
import com.sacada.figma2sdui.data.nodes.RootDocument

interface Converter {
    fun convert(componentRoot: RootDocument): JsonObject
}
