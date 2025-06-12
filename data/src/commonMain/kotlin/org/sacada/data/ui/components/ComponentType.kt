package org.sacada.data.ui.components

sealed class ComponentType(
    val type: String,
) {
    data object Box : ComponentType("Box")

    data object Button : ComponentType("Button")

    data object BottomBar : ComponentType("BottomBar")

    data object Checkbox : ComponentType("Checkbox")

    data object Column : ComponentType("Column")

    data object FloatingActionButton : ComponentType("FloatingActionButton")

    data object Icon : ComponentType("Icon")

    data object IconButton : ComponentType("IconButton")

    data object Image : ComponentType("Image")

    data object Row : ComponentType("Row")

    data object Text : ComponentType("Text")

    data object TextField : ComponentType("TextField")

    data object TopBar : ComponentType("TopBar")

    data object Switch : ComponentType("Switch")

    companion object {
        fun fromType(value: String): ComponentType =
            when (value) {
                "BOX", "Box" -> Box
                "BUTTON", "Button" -> Button
                "BOTTOM_BAR", "BottomBar" -> BottomBar
                "CHECKBOX", "Checkbox" -> Checkbox
                "COLUMN", "Column" -> Column
                "FloatingActionButton" -> FloatingActionButton
                "Icon" -> Icon
                "IconButton" -> IconButton
                "Image" -> Image
                "ROW", "Row" -> Row
                "TEXT", "Text" -> Text
                "TEXT_FIELD", "TextField" -> TextField
                "TOP_BAR", "TopBar" -> TopBar
                "SWITCH", "Switch" -> Switch
                else -> error("Unknown ComponentType: $value")
            }
    }
}
