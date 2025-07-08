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

    data object List : ComponentType("List")

    data object ListItem : ComponentType("ListItem")

    data object Row : ComponentType("Row")

    data object Switch : ComponentType("Switch")

    data object Text : ComponentType("Text")

    data object TextField : ComponentType("TextField")

    data object TopBar : ComponentType("TopBar")

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
                "List" -> List
                "ListItem" -> ListItem
                "ROW", "Row" -> Row
                "Switch" -> Switch
                "TEXT", "Text" -> Text
                "TEXT_FIELD", "TextField" -> TextField
                "TOP_BAR", "TopBar" -> TopBar
                else -> error("Unknown ComponentType: $value")
            }
    }
}
