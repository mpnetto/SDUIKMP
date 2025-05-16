package org.sacada.figma2sdui.parser

import org.sacada.figma2sdui.data.Result
import org.sacada.figma2sdui.data.nodes.RootDocument

interface Parser {
    fun parse(input: String): Result<RootDocument>
}
