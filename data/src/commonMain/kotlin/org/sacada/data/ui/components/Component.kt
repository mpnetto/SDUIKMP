package org.sacada.data.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import org.sacada.core.model.ViewComponent
import org.sacada.figma2sdui.data.nodes.Instance
import org.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription

interface Component {
    interface Renderer {
        @Composable
        fun Render(
            component: ViewComponent,
            modifier: Modifier? = null,
        )
    }

    interface Generator {
        fun generateJson(
            instance: Instance,
            componentDescriptions: Map<String, RootComponentDescription>? = null,
            performAction: ((MutableMap<String, JsonElement>) -> Unit)? = null,
        ): JsonObject
    }
}
