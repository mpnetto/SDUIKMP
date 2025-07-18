# 🏹 Server-Driven UI - Dynamic UI from Figma

This project is a **Server-Driven UI** (SDUI) implementation in **Kotlin**, allowing UI components to be dynamically rendered based on **Figma designs**. The system fetches data from the **Figma API**, processes it into a structured JSON format, and renders it using **Jetpack Compose**.

## 📌 Technologies Used

- **Kotlin**
- **Jetpack Compose**
- **Kotlinx Serialization**
- **Figma API Integration**
- **Server-Driven UI Architecture**
- **Koin (Dependency Injection)**
- **ViewModel & State Management**
- **Coroutines**
- **Ktor HTTP Client**

---

## 📁 Project Structure

```
📂 server-driven-ui
 ┣ 📂 app (Main Application)
 ┃ ┣ 📜 App.kt
 ┃ ┣ 📜 Module.kt
 ┃ ┣ 📜 Koin.kt
 ┃ ┣ 📜 MainScreen.kt
 ┃ ┣ 📜 MainScreenViewModel.kt
 ┣ 📂 core (Core Models & Utilities)
 ┃ ┣ 📜 Action.kt
 ┃ ┣ 📜 ViewComponent.kt
 ┃ ┣ 📜 ViewScreen.kt
 ┃ ┣ 📜 JsonParser.kt
 ┃ ┣ 📜 Extensions.kt
 ┣ 📂 data (Component Rendering & State Management)
 ┃ ┣ 📜 Component.kt
 ┃ ┣ 📜 ComponentRegistry.kt
 ┃ ┣ 📜 RenderComponent.kt
 ┃ ┣ 📜 ScreenViewModel.kt
 ┃ ┣ 📜 BottomBarRenderer.kt
 ┣ 📂 jsonbuilder (JSON Generation)
 ┃ ┣ 📜 Converter.kt
 ┃ ┣ 📜 FigmaJsonConverter.kt
 ┃ ┣ 📜 JsonBuilderVisitor.kt
 ┃ ┣ 📜 Main.kt
 ┣ 📂 figma2sdui (Figma API Fetching & Processing)
 ┃ ┣ 📜 APIClient.kt
 ┃ ┣ 📜 FigmaAPIClient.kt
 ┃ ┣ 📜 AnalyserVisitor.kt
 ┃ ┣ 📜 ComponentType.kt
 ┃ ┣ 📜 FigmaComposeConverter.kt
 ┃ ┣ 📜 FigmaGsonParser.kt
```

---

## 📌 How It Works

### 1️⃣ Fetching Figma Data

The system retrieves **Figma UI designs** via the **Figma API**.

```kotlin
val apiClient = ServiceCreator(APIClient.BASE_URL).createAPIClient()
val figmaClient = FigmaAPIClient(apiClient)

val response = figmaClient.loadFromApi(figmaFileId, apiToken)
```

### 2️⃣ Parsing & Processing

- **Figma JSON Data** is parsed into structured UI components.
- **Component Types** (buttons, text fields, layouts) are mapped to SDUI elements.

```kotlin
val parser = FigmaGsonParser()
val rootDocument = parser.parse(figmaJson)
```

### 3️⃣ Converting to SDUI JSON

- UI components are **converted into Server-Driven UI JSON**.

```kotlin
val converter = FigmaComposeConverter()
val jsonOutput = converter.convert(figmaJson)
```

### 4️⃣ Rendering in Jetpack Compose

- The **parsed JSON** is rendered dynamically using **Jetpack Compose**.

```kotlin
@Composable
fun MainScreen() {
    val viewModel = koinViewModel<MainScreenViewModel>()
    val rootComponent by viewModel.rootComponent

    rootComponent?.screens?.getOrNull(0)?.let { RenderScreen(it) }
}
```

---

## 📌 Example Input & Output

### **Figma Input:**
A **Figma design** converted into JSON.

```json
{
  "screens": [
    {
      "id": "screen_1",
      "type": "ScreenFrame",
      "layout": {
        "id": "column_1",
        "type": "Column",
        "attributes": {
          "spacing": 20
        },
        "children": [
          {
            "id": "btn_1",
            "type": "Button",
            "attributes": {
              "label": "Click Me"
            }
          }
        ]
      }
    }
  ]
}
```

### **Rendered Output:**
- **Dynamically generated UI** based on JSON.
- **Jetpack Compose components** are instantiated dynamically.
- **Swipe gestures** allow navigation between screens.

---

## 📌 Module Breakdown

### 🔧 **Core Module**
- Defines **core models** (`ViewComponent`, `ViewScreen`, `Action`).
- Provides **JSON parsing utilities** (`JsonParser.kt`).

### 🎨 **Data Module**
- Manages **UI rendering** (`RenderComponent.kt`).
- Uses **Jetpack Compose** to instantiate UI components dynamically.
- Handles **state management** with **ViewModel & StateFlow**.

### 🔄 **JsonBuilder Module**
- Converts **Figma component data** into structured **Server-Driven UI JSON**.

### ✨ **Compose Generator**

- Generates explicit Compose code from Figma designs.
- Uses `RenderScreen.kt` as the template for the base layout of each screen.
- Also extracts `TopBarRenderer.Render`, `BottomBarRenderer.Render` and
  `RenderComponent` so updates in these functions propagate to the generated
  code automatically.

### 📡 **Figma2SDUI Module**
- Fetches **Figma API data**.
- Parses Figma JSON into structured objects.
- Analyzes **Figma components** for UI generation.

### 🏗 **Main Application**
- Initializes **Koin dependency injection**.
- Manages **screen navigation & gestures**.
- Fetches and renders **UI dynamically**.

---

## 🚀 Next Steps

✅ **Figma API Integration**  
✅ **JSON Parsing & Component Analysis**  
✅ **Server-Driven UI Rendering**  
✅ **Gesture Navigation & Transitions**  
🔜 **Performance Optimizations**  
🔜 **Support for Additional Components**  

---

## 📌 How to Run the Project

### 📦 Dependencies
Ensure you have the following installed:
- **Android Studio** (latest version)
- **Kotlin**
- **Jetpack Compose**
- **Ktor HTTP Client**
- **Koin Dependency Injection**

### 🔧 Setup

1️⃣ **Clone the repository**
```bash
git clone https://github.com/your-repo/server-driven-ui.git
cd server-driven-ui
```

2️⃣ **Configure your Figma API keys**

The project retrieves keys from `AppConfigProvider` through build configuration. Add `FIGMA_API_KEY` and `FIGMA_FILE_KEY` to `local.properties`.
- Android reads these via `AppConfigProvider.android.kt`.
- Native targets obtain the values from environment variables through `AppConfigProvider.native.kt`.

3️⃣ **Run the project**
```bash
./gradlew build
./gradlew installDebug
```

4️⃣ **View the dynamically rendered UI!** 🎨🚀

---

## 🏗 Future Enhancements

🔹 **Animation Enhancements:** Improve UI fluidity with **MotionLayout & Compose Animations**.  
🔹 **Theming Support:** Allow **theme customization via JSON** (light/dark mode).  
🔹 **Component Expansion:** Add support for **new components (dropdowns, sliders, etc.)**.  
🔹 **Offline Support:** Cache UI components for **offline use**.  
🔹 **Performance Tuning:** Optimize JSON parsing and rendering efficiency.  

---
## 🍎 Running the iOS App

The project also ships with an **iOS target**. Open the Xcode project and run it just like any regular iOS app:

```bash
open iosApp/iosApp.xcodeproj
```

> The iOS module shares the same Kotlin code through Kotlin Multiplatform.

## 📚 Additional READMEs

Each major module contains its own README with more details:

- [composeApp](composeApp/readme.md) – main application and dependency injection
- [core](core/readme.md) – fundamental models and JSON parsing utilities
- [data](data/readme.md) – component rendering and state management
- [jsonbuilder](jsonbuilder/readme.md) – converts Figma designs into SDUI JSON
- [figma2sdui](figma2sdui/readme.md) – fetches and prepares data from Figma

---

