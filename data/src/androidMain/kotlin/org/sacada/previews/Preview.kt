package org.sacada.previews

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.sacada.data.ui.components.textField.TextFieldRenderer
import org.sacada.data.util.parseJson

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun PreviewRenderTextField() {
    val testComponent =
        """
        {
          "id": "usernameField",
          "type": "TextField",
          "attributes": {
            "placeholder": "Digite aqui o seu email",
            "supportingText": "Voce pode digitar o seu email aqui",
            "label": "Email",
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
