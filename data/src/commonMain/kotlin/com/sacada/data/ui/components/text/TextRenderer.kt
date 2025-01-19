package com.sacada.data.ui.components.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.sacada.annotation.RegisterComponent
import com.sacada.core.model.ViewComponent
import com.sacada.core.util.getStringAttribute
import com.sacada.data.ui.components.Component
import com.sacada.data.util.getPadding
import com.sacada.data.util.getTextStyle
import com.sacada.data.util.parseJson

@RegisterComponent
object TextRenderer : Component.Renderer {
    @Composable
    override fun Render(component: ViewComponent, modifier: Modifier?) {
        val textStyle = component.getTextStyle()
        val padding = component.getPadding()

        Text(
            text = component.getStringAttribute("content"),
            style = textStyle,
            modifier = Modifier.padding(padding),
            textAlign = TextAlign.Center
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
        TextRenderer.Render(component = testComponent)
    }
}
