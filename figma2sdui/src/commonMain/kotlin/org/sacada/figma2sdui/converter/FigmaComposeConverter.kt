package org.sacada.figma2sdui.converter

import org.sacada.figma2sdui.analyser.AnalyserResult
import org.sacada.figma2sdui.analyser.AnalyserVisitor
import org.sacada.figma2sdui.data.Visitor
import org.sacada.figma2sdui.data.nodes.RootDocument
import org.sacada.figma2sdui.parser.FigmaGsonParser
import org.sacada.figma2sdui.parser.Parser

class FigmaComposeConverter(
    private val parser: Parser = FigmaGsonParser(),
    private val analyser: Visitor<AnalyserResult> = AnalyserVisitor(),
) : Converter {
    override fun convert(input: String): RootDocument? {
        val parserResult = parser.parse(input)
        if (parserResult.hasError) {
            return null
        }

        val componentRoot = parserResult.data as RootDocument

        val analyserResult = componentRoot.accept(analyser)

        if (!analyserResult.errorMessages.isNullOrEmpty()) {
            return null
        }

        return componentRoot
    }
}
