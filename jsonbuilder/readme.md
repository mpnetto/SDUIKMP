# 🏹 Server-Driven UI - JsonBuilder Module

The **JsonBuilder module** is responsible for **converting Figma designs into JSON structures** for Server-Driven UI. It processes **Figma API data**, parses component structures, and generates JSON representations that can be used in the UI.

## 📌 Technologies Used

- **Kotlin**
- **Kotlinx Serialization**
- **Figma API**
- **Visitor Pattern**
- **Server-Driven UI Parsing**

## 📁 `jsonbuilder` Module Structure

```
📂 jsonbuilder
 ┣ 📂 converter
 ┃ ┣ 📜 Converter.kt
 ┃ ┣ 📜 FigmaJsonConverter.kt
 ┣ 📂 generator
 ┃ ┣ 📜 JsonBuilderVisitor.kt
 ┣ 📜 Main.kt
```

---

## 📌 Core Components

### `Converter.kt`
Defines a generic **Converter interface** that transforms a **RootDocument** into a **JSON structure**.

```kotlin
interface Converter {
    fun convert(componentRoot: RootDocument): JsonObject
}
```

### `FigmaJsonConverter.kt`
Implements the **Converter interface**, using the **JsonBuilderVisitor** to parse components and generate **Server-Driven UI JSON**.

```kotlin
class FigmaJsonConverter(
    private val generator: Visitor<JsonObject> = JsonBuilderVisitor()
) : Converter {
    override fun convert(componentRoot: RootDocument): JsonObject {
        return componentRoot.accept(generator)
    }
}
```

### `JsonBuilderVisitor.kt`
Implements the **Visitor pattern**, traversing Figma's component tree and converting elements into **Server-Driven UI JSON components**.

```kotlin
class JsonBuilderVisitor : Visitor<JsonObject> {
    private lateinit var componentDescriptions: Map<String, RootComponentDescription>
    private val screens = mutableListOf<JsonObject>()

    override fun visit(rootDocument: RootDocument, additionalData: AdditionalData?): JsonObject {
        this.componentDescriptions = rootDocument.componentDescriptions

        rootDocument.document.accept(this, null)

        return buildJsonObject {
            put("screens", buildJsonArray {
                screens.forEach { screenJson -> add(screenJson) }
            })
        }
    }
}
```

---

## 🛠 How It Works

### 1️⃣ Fetching Data from Figma API

The **`Main.kt`** file initiates the conversion process by **fetching Figma data** and converting it into JSON.

```kotlin
fun convertFigmaData(apiKey: String, fileKey: String, onResult: (JsonObject?) -> Unit) {
    fetchFigmaData(apiKey, fileKey) { rootDocument ->
        val converter = FigmaJsonConverter()

        onResult(converter.convert(rootDocument))
    }
}
```

### 2️⃣ Parsing Figma Elements

The **`JsonBuilderVisitor`** iterates over Figma components (Frames, Columns, Rows, Buttons, etc.) and converts them into JSON.

```kotlin
override fun visit(frame: Frame, additionalData: AdditionalData?): JsonObject {
    return buildJsonObject {
        put("id", JsonPrimitive(frame.id))
        put("type", JsonPrimitive("ScreenFrame"))

        val attributesJson = buildJsonObject {
            put("paddingLeft", JsonPrimitive(frame.paddingLeft))
            put("paddingRight", JsonPrimitive(frame.paddingRight))
            put("paddingTop", JsonPrimitive(frame.paddingTop))
            put("paddingBottom", JsonPrimitive(frame.paddingBottom))
        }
        put("attributes", attributesJson)
    }
}
```

### 3️⃣ Output Example

After processing, the output JSON follows the **Server-Driven UI structure**, ready for rendering in the app.

```json
{
  "screens": [
    {
      "id": "screen_1",
      "type": "ScreenFrame",
      "attributes": {
        "paddingLeft": 16,
        "paddingRight": 16,
        "paddingTop": 8,
        "paddingBottom": 8
      },
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

---

