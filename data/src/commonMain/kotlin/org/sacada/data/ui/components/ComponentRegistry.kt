package org.sacada.data.ui.components

import org.sacada.data.ui.components.Component.Generator
import org.sacada.data.ui.components.Component.Renderer
import org.sacada.data.ui.components.bottomBar.BottomBarGenerator
import org.sacada.data.ui.components.bottomBar.BottomBarRenderer
import org.sacada.data.ui.components.box.BoxGenerator
import org.sacada.data.ui.components.box.BoxRenderer
import org.sacada.data.ui.components.button.ButtonRenderer
import org.sacada.data.ui.components.checkbox.CheckboxGenerator
import org.sacada.data.ui.components.checkbox.CheckboxRenderer
import org.sacada.data.ui.components.column.ColumnRenderer
import org.sacada.data.ui.components.floatingActionButton.FloatingActionButtonGenerator
import org.sacada.data.ui.components.floatingActionButton.FloatingActionButtonRenderer
import org.sacada.data.ui.components.icon.IconGenerator
import org.sacada.data.ui.components.icon.IconRenderer
import org.sacada.data.ui.components.iconButton.IconButtonGenerator
import org.sacada.data.ui.components.iconButton.IconButtonRenderer
import org.sacada.data.ui.components.image.ImageGenerator
import org.sacada.data.ui.components.image.ImageRenderer
import org.sacada.data.ui.components.row.RowGenerator
import org.sacada.data.ui.components.row.RowRenderer
import org.sacada.data.ui.components.text.TextGenerator
import org.sacada.data.ui.components.text.TextRenderer
import org.sacada.data.ui.components.textField.TextFieldGenerator
import org.sacada.data.ui.components.textField.TextFieldRenderer
import org.sacada.data.ui.components.switch.SwitchGenerator
import org.sacada.data.ui.components.switch.SwitchRenderer
import org.sacada.data.ui.components.topBar.TopBarGenerator
import org.sacada.data.ui.components.topBar.TopBarRenderer

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
            ComponentType.Switch -> SwitchRenderer
        }

    fun getGenerator(type: String): Generator =
        when (ComponentType.fromType(type)) {
            ComponentType.Box -> BoxGenerator
            ComponentType.Button -> org.sacada.data.ui.components.button.ButtonGenerator
            ComponentType.BottomBar -> BottomBarGenerator
            ComponentType.Checkbox -> CheckboxGenerator
            ComponentType.Column -> org.sacada.data.ui.components.column.ColumnGenerator
            ComponentType.FloatingActionButton -> FloatingActionButtonGenerator
            ComponentType.Icon -> IconGenerator
            ComponentType.IconButton -> IconButtonGenerator
            ComponentType.Image -> ImageGenerator
            ComponentType.Row -> RowGenerator
            ComponentType.Text -> TextGenerator
            ComponentType.TextField -> TextFieldGenerator
            ComponentType.TopBar -> TopBarGenerator
            ComponentType.Switch -> SwitchGenerator
        }
}
