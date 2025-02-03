# 🏹 Server-Driven UI - Core Module

This module is the foundation of the **Server-Driven UI**, responsible for **JSON parsing**, **defining UI components**, **handling navigation actions**, and **screen structure**. It provides the essential classes for dynamically building the user interface.

## 📌 Technologies Used

- **Kotlin**
- **Kotlinx Serialization**
- **Server-Driven UI**
- **Modular Architecture**

## 📁 `core` Module Structure

```
📂 core
 ┣ 📂 model
 ┃ ┣ 📜 Action.kt
 ┃ ┣ 📜 ViewComponent.kt
 ┃ ┣ 📜 ViewScreen.kt
 ┣ 📂 util
 ┃ ┣ 📜 JsonParser.kt
 ┃ ┣ 📜 Extensions.kt
```

## 📌 Main Models

### `Action.kt`
Defines system actions, such as **navigation** and **events**.

```kotlin
@Serializable
data class Action(
    val type: String,
    val destination: String? = null
)
```

### `ViewComponent.kt`
Represents a UI component, allowing the dynamic creation of the interface.

```kotlin
@Serializable
data class ViewComponent(
    val id: String = "",
    val type: String,
    val attributes: Map<String, JsonElement> = emptyMap(),
    val action: Action? = null,
    val children: List<ViewComponent> = emptyList()
)
```

### `ViewScreen.kt`
Defines the screen structure, including **topBar**, **bottomBar**, and **layout**.

```kotlin
@Serializable
data class ViewScreen(
    val id: String? = null,
    val type: String? = null,
    val attributes: Map<String, JsonElement> = emptyMap(),
    val topBar: ViewComponent? = null,
    val bottomBar: ViewComponent? = null,
    val layout: ViewComponent? = null
)
```

## ⚙️ Features

- **Dynamically build screens** from a JSON file.
- **Safe JSON parsing** using `JsonParser.kt`.
- **Attribute handling** with `Extensions.kt`.
- **Action execution** (navigation, events) with `Action.kt`.

## 🛠 How to Use

1. **Provide a JSON representing the screen**
2. **Parse the JSON using `JsonParser`**
3. **Process the components in the UI**
4. **Handle navigation and event actions**

### 📌 JSON Example

```json
{
  "screens": [
    {
      "id": "home",
      "type": "screen",
      "layout": {
        "id": "mainLayout",
        "type": "column",
        "children": [
          {
            "id": "welcomeText",
            "type": "text",
            "attributes": {
              "text": "Welcome to the App!"
            }
          },
          {
            "id": "buttonStart",
            "type": "button",
            "attributes": {
              "text": "Get Started"
            },
            "action": {
              "type": "navigate",
              "destination": "nextScreen"
            }
          }
        ]
      }
    }
  ]
}
```
