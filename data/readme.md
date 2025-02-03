# 🏹 Server-Driven UI - Data Module

The **data module** is responsible for **component rendering and generation**, **UI logic**, **state management**, and **utility functions** in the Server-Driven UI architecture. It processes JSON structures and dynamically renders components using **Jetpack Compose**.

## 📌 Technologies Used

- **Kotlin**
- **Jetpack Compose**
- **Kotlinx Serialization**
- **Android ViewModel**
- **StateFlow**
- **Scaffold (Material 3)**

## 📁 `data` Module Structure

```
📂 data
 ┣ 📂 ui
 ┃ ┣ 📂 components
 ┃ ┃ ┣ 📜 Component.kt
 ┃ ┃ ┣ 📜 ComponentHelper.kt
 ┃ ┃ ┣ 📜 ComponentRegistry.kt
 ┃ ┃ ┣ 📜 ComponentType.kt
 ┃ ┃ ┣ 📜 RenderComponent.kt
 ┃ ┣ 📂 screen
 ┃ ┃ ┣ 📜 ScreenViewModel.kt
 ┃ ┃ ┣ 📜 RenderScreen.kt
 ┣ 📂 util
 ┃ ┣ 📜 Extensions.kt
 ┃ ┣ 📜 IconResources.kt
```

## 📌 Core Components

### `Component.kt`
Defines the **Component interface** with separate responsibilities:
- **Renderer**: Responsible for rendering UI components using Jetpack Compose.
- **Generator**: Converts instances into JSON structures.

```kotlin
interface Component {
    interface Renderer {
        @Composable
        fun Render(component: ViewComponent, modifier: Modifier? = null)
    }

    interface Generator {
        fun generateJson(
            instance: Instance,
            componentDescriptions: Map<String, RootComponentDescription>? = null,
            performAction: ((MutableMap<String, JsonElement>) -> Unit)? = null
        ): JsonObject
    }
}
```

### `ComponentRegistry.kt`
Handles **component registration**, linking component types to their respective **renderers and generators**.

```kotlin
object ComponentRegistry {
    fun getRenderer(type: String): Renderer = when (ComponentType.fromType(type)) {
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
}
```

### `ScreenViewModel.kt`
Manages **component states**, ensuring **validation** and **UI consistency** across different screens.

```kotlin
class ScreenViewModel : ViewModel() {
    private val _componentStates = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val componentStates: StateFlow<Map<String, Boolean>> = _componentStates

    fun updateComponentState(componentId: String, isValid: Boolean) {
        _componentStates.value = _componentStates.value.toMutableMap().apply {
            this[componentId] = isValid
        }
    }
}
```

### `RenderScreen.kt`
Handles **screen rendering**, integrating **TopBar, BottomBar**, and the main layout dynamically.

```kotlin
@Composable
fun RenderScreen(screen: ViewScreen) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            screen.topBar?.let { TopBarRenderer.Render(it) }
        },
        bottomBar = {
            screen.bottomBar?.let { BottomBarRenderer.Render(it) }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            screen.layout?.let { RenderComponent(it) }
        }
    }
}
```

---

## 🔧 Utilities

### `Extensions.kt`
Provides **helper functions** for attribute parsing, padding handling, and alignment resolution.
