package com.sacada.data.ui.components

import com.sacada.data.ui.components.Component.Generator
import com.sacada.data.ui.components.Component.Renderer
import com.sacada.data.ui.components.bottomBar.BottomBarGenerator
import com.sacada.data.ui.components.bottomBar.BottomBarRenderer
import com.sacada.data.ui.components.box.BoxGenerator
import com.sacada.data.ui.components.box.BoxRenderer
import com.sacada.data.ui.components.button.ButtonGenerator
import com.sacada.data.ui.components.button.ButtonRenderer
import com.sacada.data.ui.components.checkbox.CheckboxGenerator
import com.sacada.data.ui.components.checkbox.CheckboxRenderer
import com.sacada.data.ui.components.column.ColumnRenderer
import com.sacada.data.ui.components.column.ColumnGenerator
import com.sacada.data.ui.components.floatingActionButton.FloatingActionButtonGenerator
import com.sacada.data.ui.components.floatingActionButton.FloatingActionButtonRenderer
import com.sacada.data.ui.components.icon.IconGenerator
import com.sacada.data.ui.components.icon.IconRenderer
import com.sacada.data.ui.components.iconButton.IconButtonGenerator
import com.sacada.data.ui.components.iconButton.IconButtonRenderer
import com.sacada.data.ui.components.image.ImageGenerator
import com.sacada.data.ui.components.image.ImageRenderer
import com.sacada.data.ui.components.row.RowGenerator
import com.sacada.data.ui.components.row.RowRenderer
import com.sacada.data.ui.components.text.TextGenerator
import com.sacada.data.ui.components.text.TextRenderer
import com.sacada.data.ui.components.textField.TextFieldGenerator
import com.sacada.data.ui.components.textField.TextFieldRenderer
import com.sacada.data.ui.components.topBar.TopBarGenerator
import com.sacada.data.ui.components.topBar.TopBarRenderer

object ComponentRegistry {

    fun getRenderer(type: String): Renderer =
        when (ComponentType.fromType(type)) {
            ComponentType.Box -> BoxRenderer
            ComponentType.Button -> ButtonRenderer
            ComponentType.BottomBar -> BottomBarRenderer
            ComponentType.Checkbox -> CheckboxRenderer
            ComponentType.Column -> ColumnRenderer
            ComponentType.FloatingActionButton -> FloatingActionButtonRenderer
            ComponentType.Icon -> IconRenderer
            ComponentType.IconButton -> IconButtonRenderer
            ComponentType.Image -> ImageRenderer
            ComponentType.Row -> RowRenderer
            ComponentType.Text -> TextRenderer
            ComponentType.TextField -> TextFieldRenderer
            ComponentType.TopBar -> TopBarRenderer
        }

    fun getGenerator(type: String): Generator =
        when (ComponentType.fromType(type)) {
            ComponentType.Box -> BoxGenerator
            ComponentType.Button -> ButtonGenerator
            ComponentType.BottomBar -> BottomBarGenerator
            ComponentType.Checkbox -> CheckboxGenerator
            ComponentType.Column -> ColumnGenerator
            ComponentType.FloatingActionButton -> FloatingActionButtonGenerator
            ComponentType.Icon -> IconGenerator
            ComponentType.IconButton -> IconButtonGenerator
            ComponentType.Image -> ImageGenerator
            ComponentType.Row -> RowGenerator
            ComponentType.Text -> TextGenerator
            ComponentType.TextField -> TextFieldGenerator
            ComponentType.TopBar -> TopBarGenerator
        }

}
