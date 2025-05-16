package org.sacada.figma2sdui.data.nodes.properties

import kotlinx.serialization.Serializable
import org.sacada.figma2sdui.data.nodes.enums.TextAlignHorizontal
import org.sacada.figma2sdui.data.nodes.enums.TextAlignVertical
import org.sacada.figma2sdui.data.nodes.enums.TextAutoResize
import org.sacada.figma2sdui.data.nodes.enums.TextCase
import org.sacada.figma2sdui.data.nodes.enums.TextDecoration

@Serializable
data class TypeStyle(
    val fontFamily: String,
    val fontPostScriptName: String? = null,
    val paragraphSpacing: Int = 0,
    val paragraphIndent: Int = 0,
    val listSpacing: Int = 0,
    val italic: Boolean = false,
    val fontWeight: Int = 400,
    val fontSize: Double = 16.0,
    val textCase: TextCase = TextCase.TITLE,
    val textDecoration: TextDecoration? = null,
    val textAutoResize: TextAutoResize = TextAutoResize.HEIGHT,
    val textAlignHorizontal: TextAlignHorizontal = TextAlignHorizontal.LEFT,
    val textAlignVertical: TextAlignVertical = TextAlignVertical.TOP,
    val letterSpacing: Double = 0.0,
    val fills: Array<Paint> = emptyArray(),
    val lineHeightPx: Double = 0.0,
    val lineHeightPercent: Double = 100.0,
    val lineHeightPercentFontSize: Double = 100.0,
    val lineHeightUnit: String = "PIXELS",
)
