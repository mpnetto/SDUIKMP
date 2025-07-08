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
            when (value.lowercase()) {
                "box" -> Box
                "Button" -> Button
                "bottom_bar", "bottombar" -> BottomBar
                "checkbox" -> Checkbox
                "column" -> Column
                "floatingactionbutton" -> FloatingActionButton
                "icon" -> Icon
                "iconbutton" -> IconButton
                "image" -> Image
                "list" -> List
                "listitem" -> ListItem
                "row" -> Row
                "switch" -> Switch
                "text" -> Text
                "text_field", "textfield" -> TextField
                "top_bar", "topbar" -> TopBar
                else -> error("Unknown ComponentType: $value")
            }
    }
}
