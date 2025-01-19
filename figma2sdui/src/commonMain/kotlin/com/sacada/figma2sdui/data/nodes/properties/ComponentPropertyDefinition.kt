package com.sacada.figma2sdui.data.nodes.properties

import com.sacada.figma2sdui.data.nodes.enums.ComponentPropertyType
import kotlinx.serialization.Serializable

@Serializable
data class ComponentPropertyDefinition(
    val type: ComponentPropertyType,
    val defaultValue: String,
    val variantOptions: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ComponentPropertyDefinition

        if (type != other.type) return false
        if (defaultValue != other.defaultValue) return false
        if (!variantOptions.contentEquals(other.variantOptions)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + defaultValue.hashCode()
        result = 31 * result + variantOptions.contentHashCode()
        return result
    }

}
