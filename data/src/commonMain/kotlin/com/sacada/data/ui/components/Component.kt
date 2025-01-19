package com.sacada.data.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.json.JsonObject
import com.sacada.core.model.ViewComponent
import com.sacada.figma2sdui.data.nodes.Instance
import com.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription
import kotlinx.serialization.json.JsonElement

interface Component {

    interface Renderer {
        @Composable
        fun Render(component: ViewComponent, modifier: Modifier? = null)
    }

    interface Generator {
        fun generateJson(
            instance: Instance,
            componentDescriptions: Map<String, RootComponentDescription>? = null,
            performAction: ((MutableMap<String, JsonElement>) -> Unit)? = null
        ): JsonObject
    }
}
