package org.sacada.data.ui.components.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.sacada.annotation.RegisterComponent
import org.sacada.core.blueprint.Blueprint
import org.sacada.core.blueprint.TextBlueprint
import org.sacada.core.model.ViewComponent
import org.sacada.data.ui.components.Component
import org.sacada.data.util.parseJson

@RegisterComponent
object TextRenderer : Component.Renderer {
    @Composable
    override fun Render(
        blueprint: Blueprint,
        modifier: Modifier?,
    ) {
        blueprint as TextBlueprint
        val textStyle = when (blueprint.styleType) {
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
        val paddingModifier = Modifier.padding(
            start = blueprint.paddingLeft.dp,
            end = blueprint.paddingRight.dp,
            top = blueprint.paddingTop.dp,
            bottom = blueprint.paddingBottom.dp,
        )

        Text(
            text = blueprint.content,
            style = textStyle,
            modifier = paddingModifier,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun PreviewRenderText() {
    val testComponent =
        """
        {
            "id": "text1",
            "type": "Text",
            "attributes": {
                "content": "What are you interested in?",
                "style": {
                    "type": "titleLarge",
                    "color": "black"
                }
            }
        }
    """.parseJson()

    MaterialTheme {
        TextRenderer.Render(TextBlueprint.from(testComponent))
    }
}
