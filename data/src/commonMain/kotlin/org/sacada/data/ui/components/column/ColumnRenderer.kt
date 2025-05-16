package org.sacada.data.ui.components.column

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.sacada.annotation.RegisterComponent
import org.sacada.core.model.ViewComponent
import org.sacada.core.util.getStringAttribute
import org.sacada.data.ui.components.Component
import org.sacada.data.ui.components.RenderComponent
import org.sacada.data.util.getPadding
import org.sacada.data.util.parseJson
import org.sacada.data.util.resolveHorizontalAlignment
import org.sacada.data.util.resolveVerticalAlignment
import org.sacada.data.util.resolveVerticalArrangement

@RegisterComponent
object ColumnRenderer : Component.Renderer {
    @Composable
    override fun Render(
        component: ViewComponent,
        modifier: Modifier?,
    ) {
        val horizontalAlignment = component.resolveHorizontalAlignment()
        val verticalAlignment = component.resolveVerticalAlignment()
        val verticalArrangement = component.resolveVerticalArrangement(verticalAlignment)

        val padding = remember { component.getPadding() }
        val layoutSizingHorizontal = component.getStringAttribute("layoutSizingHorizontal")
        val layoutSizingVertical = component.getStringAttribute("layoutSizingVertical")

        val widthModifier =
            when (layoutSizingHorizontal) {
                "FILL" -> Modifier.fillMaxWidth()
                "HUG" -> Modifier.wrapContentWidth()
                else -> Modifier
            }

        val heightModifier =
            when (layoutSizingVertical) {
                "FILL" -> Modifier.fillMaxHeight()
                "HUG" -> Modifier.wrapContentHeight()
                else -> Modifier
            }

        Column(
            modifier =
                (modifier ?: Modifier)
                    .then(widthModifier)
                    .then(heightModifier)
                    .padding(padding),
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = verticalArrangement,
        ) {
            component.children.forEach { child ->
                RenderComponent(
                    child,
                )
            }
        }
    }
}

@Preview()
@Composable
fun PreviewRenderColumn_Varied() {
    val sampleComponent =
        """
        {
            "id" : "88:392",
            "type" : "Column",
            "attributes" : {
                "paddingLeft" : 0,
                "paddingRight" : 0,
                "paddingTop" : 0,
                "paddingBottom" : 0,
                "itemSpacing" : 20,
                "horizontalAlignment" : "CENTER",
                "verticalArrangement" : "",
                "layoutSizingHorizontal" : "FILL",
                "layoutSizingVertical" : "FILL"
            },
            "children" : [
                {
                "id" : "30:1879",
                "type" : "TextField",
                "attributes" : {
                    "placeholder" : "Digite aqui o seu email",
                    "supportingText" : "true",
                    "label" : "Email",
                    "showLeadingIcon" : false,
                    "showTrailingIcon" : true,
                    "validation" : {
                        "required" : true,
                        "minLength" : 5,
                        "regex" : "^[a-zA-Z0-9_]*${'$'}"
                    }
                }
            }, {
                "id" : "179:83",
                "type" : "TextField",
                "attributes" : {
                    "placeholder" : "Digite aqui o seu email",
                    "supportingText" : "true",
                    "label" : "Email",
                    "showLeadingIcon" : false,
                    "showTrailingIcon" : true,
                    "validation" : {
                        "required" : true,
                        "minLength" : 5,
                        "regex" : "^[a-zA-Z0-9_]*${'$'}"
                    }
                }
            } ]
        }
    """.parseJson()

    MaterialTheme {
        ColumnRenderer.Render(component = sampleComponent)
    }
}
