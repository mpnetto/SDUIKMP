package com.sacada.figma2sdui.parser

import com.sacada.figma2sdui.data.Result
import com.sacada.figma2sdui.data.nodes.RootDocument

interface Parser {
    fun parse(input: String): Result<RootDocument>
}
