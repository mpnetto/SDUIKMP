package org.sacada.jsonbuilder.converter

import kotlinx.serialization.json.JsonObject
import org.sacada.figma2sdui.data.Visitor
import org.sacada.figma2sdui.data.nodes.RootDocument
import org.sacada.jsonbuilder.generator.JsonBuilderVisitor

class FigmaJsonConverter(
    private val generator: Visitor<JsonObject> = JsonBuilderVisitor(),
) : Converter {
    override fun convert(componentRoot: RootDocument): JsonObject = componentRoot.accept(generator)
}
