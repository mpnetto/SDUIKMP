package org.sacada.data.ui.components.switch

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sacada.annotation.RegisterComponent
import org.sacada.core.model.ViewComponent
import org.sacada.core.util.getStringAttribute
import org.sacada.data.ui.components.Component

@RegisterComponent
object SwitchRenderer : Component.Renderer {
    @Composable
    override fun Render(
        component: ViewComponent,
        modifier: Modifier?
    ) {
        val isChecked = rememberSaveable { mutableStateOf(false) }
        val label = component.getStringAttribute("label")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier ?: Modifier
        ) {
            Switch(
                checked = isChecked.value,
                onCheckedChange = { isChecked.value = it }
            )
            if (label.isNotEmpty()) {
                Text(label, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}
