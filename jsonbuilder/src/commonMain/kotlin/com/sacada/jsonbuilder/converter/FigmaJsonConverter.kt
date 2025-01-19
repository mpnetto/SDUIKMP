package com.sacada.jsonbuilder.converter

import kotlinx.serialization.json.JsonObject
import com.sacada.figma2sdui.data.Visitor
import com.sacada.figma2sdui.data.nodes.RootDocument
import com.sacada.jsonbuilder.generator.JsonBuilderVisitor

class FigmaJsonConverter(
    private val generator: Visitor<JsonObject> = JsonBuilderVisitor()
) : Converter {
    override fun convert(componentRoot: RootDocument): JsonObject {
        return componentRoot.accept(generator)
    }
}
