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
        performAction: ((MutableMap<String, JsonElement>) -> Unit)?
    ): JsonObject {
        return buildJsonObject {
            put("id", JsonPrimitive(instance.id))
            put("type", JsonPrimitive(instance.componentType.name.convertToCamelCase()))
            put("attributes", buildJsonObject {
                instance.componentProperties.forEach { (key, value) ->
                    when {
                        key.contains("placeholder", ignoreCase = true) -> put("placeholder", JsonPrimitive(value.value))
                        key.contains("supporting", ignoreCase = true) -> put("supportingText", JsonPrimitive(value.value))
                        key.contains("label", ignoreCase = true) -> put("label", JsonPrimitive(value.value))
                        key.contains("leading", ignoreCase = true) -> put("showLeadingIcon", JsonPrimitive(value.value.toBoolean()))
                        key.contains("trailing", ignoreCase = true) -> put("showTrailingIcon", JsonPrimitive(value.value.toBoolean()))
                    }
                }
                put("validation", buildJsonObject {
                    put("required", JsonPrimitive(instance.componentProperties["required"]?.value?.toBoolean() ?: true))
                    put("minLength", JsonPrimitive(instance.componentProperties["minLength"]?.value?.toInt() ?: 5))
                    put("regex", JsonPrimitive(instance.componentProperties["regex"]?.value ?: "^[a-zA-Z0-9_]*$"))
                })
            })
        }
    }
}
