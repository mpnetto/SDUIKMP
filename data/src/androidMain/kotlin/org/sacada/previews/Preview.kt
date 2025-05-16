package org.sacada.previews

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.sacada.data.ui.components.textField.TextFieldRenderer
import org.sacada.data.util.parseJson

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
