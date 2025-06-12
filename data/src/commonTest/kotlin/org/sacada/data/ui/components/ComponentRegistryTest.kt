import kotlin.test.Test
import kotlin.test.assertEquals
import org.sacada.data.ui.components.ComponentRegistry
import org.sacada.data.ui.components.ComponentType
import org.sacada.data.ui.components.box.BoxRenderer
import org.sacada.data.ui.components.box.BoxGenerator
import org.sacada.data.ui.components.button.ButtonRenderer
import org.sacada.data.ui.components.button.ButtonGenerator
import org.sacada.data.ui.components.bottomBar.BottomBarRenderer
import org.sacada.data.ui.components.bottomBar.BottomBarGenerator
import org.sacada.data.ui.components.checkbox.CheckboxRenderer
import org.sacada.data.ui.components.checkbox.CheckboxGenerator
import org.sacada.data.ui.components.column.ColumnRenderer
import org.sacada.data.ui.components.column.ColumnGenerator
import org.sacada.data.ui.components.floatingActionButton.FloatingActionButtonRenderer
import org.sacada.data.ui.components.floatingActionButton.FloatingActionButtonGenerator
import org.sacada.data.ui.components.icon.IconRenderer
import org.sacada.data.ui.components.icon.IconGenerator
import org.sacada.data.ui.components.iconButton.IconButtonRenderer
import org.sacada.data.ui.components.iconButton.IconButtonGenerator
import org.sacada.data.ui.components.image.ImageRenderer
import org.sacada.data.ui.components.image.ImageGenerator
import org.sacada.data.ui.components.row.RowRenderer
import org.sacada.data.ui.components.row.RowGenerator
import org.sacada.data.ui.components.text.TextRenderer
import org.sacada.data.ui.components.text.TextGenerator
import org.sacada.data.ui.components.textField.TextFieldRenderer
import org.sacada.data.ui.components.textField.TextFieldGenerator
import org.sacada.data.ui.components.topBar.TopBarRenderer
import org.sacada.data.ui.components.topBar.TopBarGenerator

class ComponentRegistryTest {
    @Test
    fun testGetRenderer() {
        assertEquals(BoxRenderer, ComponentRegistry.getRenderer(ComponentType.Box.type))
        assertEquals(ButtonRenderer, ComponentRegistry.getRenderer(ComponentType.Button.type))
        assertEquals(BottomBarRenderer, ComponentRegistry.getRenderer(ComponentType.BottomBar.type))
        assertEquals(CheckboxRenderer, ComponentRegistry.getRenderer(ComponentType.Checkbox.type))
        assertEquals(ColumnRenderer, ComponentRegistry.getRenderer(ComponentType.Column.type))
        assertEquals(FloatingActionButtonRenderer, ComponentRegistry.getRenderer(ComponentType.FloatingActionButton.type))
        assertEquals(IconRenderer, ComponentRegistry.getRenderer(ComponentType.Icon.type))
        assertEquals(IconButtonRenderer, ComponentRegistry.getRenderer(ComponentType.IconButton.type))
        assertEquals(ImageRenderer, ComponentRegistry.getRenderer(ComponentType.Image.type))
        assertEquals(RowRenderer, ComponentRegistry.getRenderer(ComponentType.Row.type))
        assertEquals(TextRenderer, ComponentRegistry.getRenderer(ComponentType.Text.type))
        assertEquals(TextFieldRenderer, ComponentRegistry.getRenderer(ComponentType.TextField.type))
        assertEquals(TopBarRenderer, ComponentRegistry.getRenderer(ComponentType.TopBar.type))
    }

    @Test
    fun testGetGenerator() {
        assertEquals(BoxGenerator, ComponentRegistry.getGenerator(ComponentType.Box.type))
        assertEquals(ButtonGenerator, ComponentRegistry.getGenerator(ComponentType.Button.type))
        assertEquals(BottomBarGenerator, ComponentRegistry.getGenerator(ComponentType.BottomBar.type))
        assertEquals(CheckboxGenerator, ComponentRegistry.getGenerator(ComponentType.Checkbox.type))
        assertEquals(ColumnGenerator, ComponentRegistry.getGenerator(ComponentType.Column.type))
        assertEquals(FloatingActionButtonGenerator, ComponentRegistry.getGenerator(ComponentType.FloatingActionButton.type))
        assertEquals(IconGenerator, ComponentRegistry.getGenerator(ComponentType.Icon.type))
        assertEquals(IconButtonGenerator, ComponentRegistry.getGenerator(ComponentType.IconButton.type))
        assertEquals(ImageGenerator, ComponentRegistry.getGenerator(ComponentType.Image.type))
        assertEquals(RowGenerator, ComponentRegistry.getGenerator(ComponentType.Row.type))
        assertEquals(TextGenerator, ComponentRegistry.getGenerator(ComponentType.Text.type))
        assertEquals(TextFieldGenerator, ComponentRegistry.getGenerator(ComponentType.TextField.type))
        assertEquals(TopBarGenerator, ComponentRegistry.getGenerator(ComponentType.TopBar.type))
    }
}
