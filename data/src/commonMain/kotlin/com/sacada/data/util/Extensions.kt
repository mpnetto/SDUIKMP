package com.sacada.data.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.sacada.core.model.ViewComponent
import com.sacada.core.model.handleAction
import com.sacada.core.util.getStringAttribute
import com.sacada.core.util.getSubAttributes
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive

fun ViewComponent.getPadding(): PaddingValues {
    val padding = this.getStringAttribute("padding").toIntOrNull()?.dp
    if (padding != null) {
        return PaddingValues(all = padding)
    }
    val paddingLeft = this.getStringAttribute("paddingLeft").toIntOrNull()?.dp ?: 0.dp
    val paddingRight = this.getStringAttribute("paddingRight").toIntOrNull()?.dp ?: 0.dp
    val paddingTop = this.getStringAttribute("paddingTop").toIntOrNull()?.dp ?: 0.dp
    val paddingBottom = this.getStringAttribute("paddingBottom").toIntOrNull()?.dp ?: 0.dp
    return PaddingValues(
        start = paddingLeft,
        end = paddingRight,
        top = paddingTop,
        bottom = paddingBottom
    )
}

@Composable
fun ViewComponent.getTextStyle(group: String = "style"): TextStyle {
    val styleName = getSubAttributes(group)?.get("type")?.jsonPrimitive?.contentOrNull
    return when (styleName) {
        "displayLarge" -> MaterialTheme.typography.displayLarge
        "displayMedium" -> MaterialTheme.typography.displayMedium
        "displaySmall" -> MaterialTheme.typography.displaySmall
        "headlineLarge" -> MaterialTheme.typography.headlineLarge
        "headlineMedium" -> MaterialTheme.typography.headlineMedium
        "headlineSmall" -> MaterialTheme.typography.headlineSmall
        "titleLarge" -> MaterialTheme.typography.titleLarge
        "titleMedium" -> MaterialTheme.typography.titleMedium
        "titleSmall" -> MaterialTheme.typography.titleSmall
        "bodyLarge" -> MaterialTheme.typography.bodyLarge
        "bodyMedium" -> MaterialTheme.typography.bodyMedium
        "bodySmall" -> MaterialTheme.typography.bodySmall
        "labelLarge" -> MaterialTheme.typography.labelLarge
        "labelMedium" -> MaterialTheme.typography.labelMedium
        "labelSmall" -> MaterialTheme.typography.labelSmall
        else -> MaterialTheme.typography.bodyMedium
    }
}

fun ViewComponent.resolveHorizontalAlignment(): Alignment.Horizontal = when (
    getStringAttribute(
        "horizontalAlignment"
    )
) {
    "CenterHorizontally", "CENTER" -> Alignment.CenterHorizontally
    "Start" -> Alignment.Start
    "End" -> Alignment.End
    else -> Alignment.Start
}

fun ViewComponent.resolveVerticalAlignment(): Alignment.Vertical = when (
    getStringAttribute(
        "verticalAlignment"
    )
) {
    "CenterVertically", "CENTER" -> Alignment.CenterVertically
    "Top", "TOP" -> Alignment.Top
    "Bottom" -> Alignment.Bottom
    else -> Alignment.CenterVertically
}

fun ViewComponent.resolveVerticalArrangement(
    verticalAlignment: Alignment.Vertical
): Arrangement.Vertical = when {
    getStringAttribute("itemSpacing").toIntOrNull() != null ->
        Arrangement.spacedBy(getStringAttribute("itemSpacing").toInt().dp, verticalAlignment)
    getStringAttribute("verticalArrangement") == "SpaceBetween" -> Arrangement.SpaceBetween
    getStringAttribute("verticalArrangement") == "SpaceAround" -> Arrangement.SpaceAround
    getStringAttribute("verticalArrangement") == "SpaceEvenly" -> Arrangement.SpaceEvenly
    else -> Arrangement.spacedBy(0.dp, verticalAlignment)
}

fun ViewComponent.resolveHorizontalArrangement(): Arrangement.Horizontal = when (
    getStringAttribute(
        "horizontalArrangement"
    )
) {
    "SpaceBetween", "SPACE_BETWEEN" -> Arrangement.SpaceBetween
    "SpaceAround" -> Arrangement.SpaceAround
    "SpaceEvenly" -> Arrangement.SpaceEvenly
    "Start" -> Arrangement.Start
    "End" -> Arrangement.End
    "Center" -> Arrangement.Center
    else -> Arrangement.Start
}

@Composable
fun ViewComponent.createActions(): @Composable RowScope.() -> Unit = {
    children.filter { it.type == "Action" }.forEach { actionComponent ->
        com.sacada.data.ui.components.box.BoxRenderer.Render(actionComponent)
    }
}

fun ViewComponent.performAction() {
    action?.let { handleAction(it) }
}

fun String.parseJson(): ViewComponent = Json.decodeFromString(this)

fun String.lowercaseFirstLetter(): String =
    replaceFirstChar { ch -> ch.lowercaseChar() }

fun String.convertToCamelCase(): String {
    return split('_', '-')
        .filter { it.isNotEmpty() }
        .map { segment -> segment.lowercase() }
        .map { segment ->
            segment.replaceFirstChar { ch -> ch.uppercaseChar() }
        }
        .joinToString("")
}
