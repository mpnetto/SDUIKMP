package org.sacada.jsonbuilder.converter

import kotlinx.serialization.json.JsonObject
import org.sacada.figma2sdui.data.nodes.RootDocument

interface Converter {
    fun convert(componentRoot: RootDocument): JsonObject
}
