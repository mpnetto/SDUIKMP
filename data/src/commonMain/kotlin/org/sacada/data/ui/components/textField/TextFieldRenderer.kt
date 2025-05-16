package org.sacada.data.ui.components.textField

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.sacada.annotation.RegisterComponent
import org.sacada.core.model.ViewComponent
import org.sacada.core.util.getStringAttribute
import org.sacada.core.util.isValid
import org.sacada.data.ui.components.Component
import org.sacada.data.util.parseJson

@RegisterComponent
object TextFieldRenderer : Component.Renderer {
    @Composable
    override fun Render(
        component: ViewComponent,
        modifier: Modifier?,
    ) {
//        val viewModel = LocalScreenViewModel.current
        val textValue = remember { mutableStateOf("") }
        val isValid = remember { mutableStateOf(component.isValid("")) }

        Column {
            Text(text = component.getStringAttribute("label"))
            TextField(
                placeholder = { Text(text = component.getStringAttribute("placeholder")) },
                supportingText = { Text(text = component.getStringAttribute("supportingText")) },
                value = textValue.value,
                isError = !isValid.value,
                onValueChange = {
                    textValue.value = it
                    isValid.value = component.isValid(it)
//                    viewModel.updateComponentState(component.id, isValid.value)
                },
            )
        }
    }
}

@Preview
@Composable
fun PreviewRenderTextField() {
    val testComponent =
        """
        {
          "id": "usernameField",
          "type": "TextField",
          "attributes": {
            "placeholder": "Enter your username",
            "validation": {
              "required": true,
              "minLength": 5,
              "regex": "^[a-zA-Z0-9_]*${'$'}"
            }
          }
        }
    """.parseJson()

    MaterialTheme {
        TextFieldRenderer.Render(component = testComponent)
    }
}
