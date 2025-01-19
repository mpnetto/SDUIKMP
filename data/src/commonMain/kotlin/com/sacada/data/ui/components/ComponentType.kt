package com.sacada.data.ui.components

sealed class ComponentType(val type: String) {
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

    companion object {
        fun fromType(value: String): ComponentType = when (value) {
            "Box" -> Box
            "Button" -> Button
            "BottomBar" -> BottomBar
            "Checkbox" -> Checkbox
            "Column" -> Column
            "FloatingActionButton" -> FloatingActionButton
            "Icon" -> Icon
            "IconButton" -> IconButton
            "Image" -> Image
            "Row" -> Row
            "Text" -> Text
            "TextField" -> TextField
            "TopBar" -> TopBar
            else -> error("Unknown ComponentType: $value")
        }
    }
}
