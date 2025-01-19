package com.sacada.figma2sdui.analyser

enum class ComponentType(
    val isTag: Boolean,
    val isM3Tag: Boolean,
    val tag: String? = null,
    var additionalData: Any? = null
) {
    SCREEN_FRAME(false, false),
    COMPONENT_FRAME(false, false),
    TEXT(false, false),
    UNTAGGED(false, false),
    UNKNOWN(false, false),
    ROW(true, false, "row"),
    COLUMN(true, false, "column"),
    LIST(true, false, "list"),

    BUTTON(true, true, "button"),
    TEXT_FIELD(true, true, "text-field"),
    LIST_ITEM(true, true, "list-item"),
    CHECKBOX(true, true, "checkbox"),
    SWITCH(true, true, "switch"),
    TOP_BAR(true, true, "top-app-bar"),
    BOTTOM_BAR(true, true, "bottom-app-bar");

    companion object {
        private val taggedComponentTypes = entries.filter { componentType -> componentType.isTag }
        fun findTaggedComponentType(componentName: String): ComponentType {
            val match = Regex("\\[(.*?)]").find(componentName) ?: return UNTAGGED
            val (valueMatched) = match.destructured

            return taggedComponentTypes.find { componentType ->
                valueMatched.startsWith(
                    componentType.tag!!
                )
            } ?: return UNKNOWN
        }

        fun findListDensity(componentName: String): Int {
            val match = Regex("\\[(.*?)]").find(componentName)
            val (nameMatched) = match!!.destructured

            return nameMatched.split(":").last().toInt()
        }
    }
}
