package com.sacada.figma2sdui.converter

import com.sacada.figma2sdui.data.nodes.RootDocument

interface Converter {
    fun convert(input: String): RootDocument?
}
