package org.sacada.data.ui.components.box

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sacada.annotation.RegisterComponent
import org.sacada.core.blueprint.Blueprint
import org.sacada.core.blueprint.BoxBlueprint
import org.sacada.data.navigation.LocalNavigator
import org.sacada.data.ui.components.Component
import org.sacada.data.ui.components.RenderComponent

@RegisterComponent
object BoxRenderer : Component.Renderer {
    @Composable
    override fun Render(
        blueprint: Blueprint,
        modifier: Modifier?,
    ) {
        blueprint as BoxBlueprint
        val navController = LocalNavigator.current

        val padding = remember {
            PaddingValues(
                start = blueprint.paddingLeft.dp,
                end = blueprint.paddingRight.dp,
                top = blueprint.paddingTop.dp,
                bottom = blueprint.paddingBottom.dp,
            )
        }
        val height = blueprint.height.dp
        val width = blueprint.width.dp

        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .width(width)
                    .height(height)
                    .padding(padding)
                    .clickable {
                        when (blueprint.action?.type) {
                            "NAVIGATE" ->
                                blueprint.action.destination?.let {
                                    navController.navigate("screen/$it")
                                }
                            "BACK" -> navController.popBackStack()
                        }
                    },
        ) {
            blueprint.children.forEach { child ->
                RenderComponent(child)
            }
        }
    }
}
