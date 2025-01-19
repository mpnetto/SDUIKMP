package com.sacada.data.ui.components.checkbox

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sacada.annotation.RegisterComponent
import com.sacada.core.model.ViewComponent
import com.sacada.core.util.getStringAttribute
import com.sacada.data.ui.components.Component
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.compose.ui.tooling.preview.Preview

@RegisterComponent
object CheckboxRenderer : Component.Renderer {
    @Composable
    override fun Render(component: ViewComponent, modifier: Modifier?) {
        val isChecked = rememberSaveable { mutableStateOf(false) }
        val labelText = component.getStringAttribute("label")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp)
        ) {
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = { isChecked.value = it }
            )
            Text(text = labelText, textAlign = TextAlign.Center)
        }
    }
}

@Preview()
@Composable
fun PreviewRenderCheckbox() {
    val sampleComponent =
        ViewComponent(
            type = "Checkbox",
            attributes =
            mapOf(
                "label" to JsonPrimitive("Accept Terms and Conditions")
            )
        )
    CheckboxRenderer.Render(component = sampleComponent)
}
