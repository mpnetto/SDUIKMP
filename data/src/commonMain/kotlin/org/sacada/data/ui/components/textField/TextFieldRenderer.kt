package org.sacada.data.ui.components.textField

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.sacada.annotation.RegisterComponent
import org.sacada.core.model.ViewComponent
import org.sacada.core.util.getStringAttribute
import org.sacada.core.util.isValid
import org.sacada.data.ui.components.Component
import org.sacada.data.ui.components.box.BoxRenderer

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

        val placeholderText = component.getStringAttribute("placeholder")
        val supportingText = component.getStringAttribute("supportingText")
        val style = component.getStringAttribute("style")
        val label = component.getStringAttribute("label")
        val leadingIcon = createLeadingIconComposable(component)
        val trailingIcon = createTrailingIconComposable(component)

        val onValueChange: (String) -> Unit = {
            textValue.value = it
            isValid.value = component.isValid(it)
            // viewModel.updateComponentState(component.id, isValid.value)
        }

        Column {
            if (style == "outlined") {
                OutlinedTextField(
                    value = textValue.value,
                    onValueChange = onValueChange,
                    label = { Text(text = label) },
                    placeholder = { Text(text = placeholderText) },
                    supportingText = { Text(text = supportingText) },
                    isError = !isValid.value,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                )
            } else {
                TextField(
                    value = textValue.value,
                    onValueChange = onValueChange,
                    placeholder = { Text(text = placeholderText) },
                    supportingText = { Text(text = supportingText) },
                    isError = !isValid.value,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                )
            }
        }
    }
}

@Composable
private fun createLeadingIconComposable(component: ViewComponent): @Composable () -> Unit =
    {
        component.children.find { it.type == "leadingIcon" }?.let {
            BoxRenderer.Render(it)
        }
    }

private fun createTrailingIconComposable(component: ViewComponent): @Composable () -> Unit =
    {
        component.children.find { it.type == "trailingIcon" }?.let {
            BoxRenderer.Render(it)
        }
    }
