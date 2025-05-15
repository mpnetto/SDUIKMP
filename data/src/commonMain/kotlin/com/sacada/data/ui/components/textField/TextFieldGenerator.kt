package com.sacada.data.ui.components.textField

import com.sacada.annotation.RegisterComponent
import com.sacada.data.ui.components.Component
import com.sacada.data.util.convertToCamelCase
import com.sacada.figma2sdui.data.nodes.Instance
import com.sacada.figma2sdui.data.nodes.properties.root.RootComponentDescription
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

@RegisterComponent
object TextFieldGenerator : Component.Generator {
    override fun generateJson(
        instance: Instance,
        componentDescriptions: Map<String, RootComponentDescription>?,
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?,
    ): JsonObject =
        buildJsonObject {
            put("id", JsonPrimitive(instance.id))
            put("type", JsonPrimitive(instance.componentType.name.convertToCamelCase()))
            put(
                "attributes",
                buildJsonObject {
                    instance.componentProperties.forEach { (key, value) ->
                        when {
                            key.contains("placeholder", ignoreCase = true) -> put("placeholder", value.value)
                            key.contains("supporting", ignoreCase = true) -> put("supportingText", value.value)
                            key.contains("label", ignoreCase = true) -> put("label", value.value)
                            key.contains("leading", ignoreCase = true) -> put("showLeadingIcon", value.value)
                            key.contains("trailing", ignoreCase = true) -> put("showTrailingIcon", value.value)
                        }
                    }
                    put(
                        "validation",
                        buildJsonObject {
                            put("required", instance.componentProperties["required"]?.value ?: JsonPrimitive(true))
                            put("minLength", instance.componentProperties["minLength"]?.value ?: JsonPrimitive(5))
                            put("regex", instance.componentProperties["regex"]?.value ?: JsonPrimitive("^[a-zA-Z0-9_]*$"))
                        },
                    )
                },
            )
        }
}
