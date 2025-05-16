package org.sacada.figma2sdui.converter

import org.sacada.figma2sdui.data.nodes.RootDocument

interface Converter {
    fun convert(input: String): RootDocument?
}
