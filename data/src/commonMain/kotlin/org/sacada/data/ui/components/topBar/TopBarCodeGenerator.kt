package org.sacada.data.ui.components.topBar

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sacada.annotation.RegisterComponent
import org.sacada.core.model.ViewComponent
import org.sacada.core.util.getIntAttribute
import org.sacada.core.util.getStringAttribute
import org.sacada.data.ui.components.Component

@RegisterComponent
object TopBarCodeGenerator : Component.CodeGenerator {
    override fun generateCode(component: ViewComponent): String {
        val scrollBehaviorType = component.getStringAttribute("scrollBehavior")
        val appBarType = component.getStringAttribute("topBarType")
        val barTitle = component.getStringAttribute("title")
        val paddingLeft = component.getIntAttribute("paddingLeft")
        val paddingRight = component.getIntAttribute("paddingRight")
        val paddingTop = component.getIntAttribute("paddingTop")
        val paddingBottom = component.getIntAttribute("paddingBottom")

        val paddingModifier =
            Modifier.padding(
                start = paddingLeft.dp,
                end = paddingRight.dp,
                top = paddingTop.dp,
                bottom = paddingBottom.dp,
            )

        val code = StringBuilder()
        code.appendLine("// Code generated for TopBar (id=${component.id})")
        code.append("TopBar(")

        code.appendLine("component = $component },")
        code.appendLine("scrollBehaviorType = $scrollBehaviorType },")
        code.appendLine("appBarType = $appBarType },")
        code.appendLine("barTitle = $barTitle },")
        code.appendLine("paddingModifier = $paddingModifier },")

        code.appendLine(")")

        return code.toString()
    }
}
