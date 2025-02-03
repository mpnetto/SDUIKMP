# 🏹 Server-Driven UI - Figma2SDUI Module

The **Figma2SDUI module** is responsible for **fetching, parsing, analyzing, and converting Figma data** into a structured format for **Server-Driven UI**. This module integrates with the **Figma API**, processes component structures, and outputs data ready for use in UI generation.

## 📌 Technologies Used

- **Kotlin**
- **Kotlinx Serialization**
- **Figma API**
- **Ktor HTTP Client**
- **Visitor Pattern**
- **Gson Parser**

## 📁 `figma2sdui` Module Structure

```
📂 figma2sdui
 ┣ 📂 analyser
 ┃ ┣ 📜 AnalyserResult.kt
 ┃ ┣ 📜 AnalyserVisitor.kt
 ┃ ┣ 📜 ComponentType.kt
 ┣ 📂 client
 ┃ ┣ 📜 ServiceCreator.kt
 ┃ ┣ 📜 APIClient.kt
 ┃ ┣ 📜 FigmaAPIClient.kt
 ┣ 📂 converter
 ┃ ┣ 📜 FigmaComposeConverter.kt
 ┃ ┣ 📜 Converter.kt
 ┣ 📂 parser
 ┃ ┣ 📜 Parser.kt
 ┃ ┣ 📜 FigmaGsonParser.kt
```

---

## 📌 Core Components

### 🔍 API Client

### `APIClient.kt`
Defines the API interface for communicating with **Figma's API**.

```kotlin
interface APIClient {
    @GET("files/{fileId}")
    suspend fun loadFromApi(
        @Path("fileId") fileId: String,
        @Header("X-Figma-Token") apiToken: String
    ): String
}
```

### `FigmaAPIClient.kt`
Handles **Figma API requests** using the defined API client.

```kotlin
class FigmaAPIClient(private val apiClient: APIClient) {
    suspend fun loadFromApi(fileId: String, apiToken: String): Result<String> {
        return try {
            val response = apiClient.loadFromApi(fileId, apiToken)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure("HTTP client send failure: ${e.message}")
        }
    }
}
```

---

## 📌 Parsing & Analysis

### `FigmaGsonParser.kt`
Parses **JSON responses** from the Figma API into **RootDocument** objects.

```kotlin
class FigmaGsonParser : Parser {
    private val json: Json = Json {
        classDiscriminator = "type"
        ignoreUnknownKeys = true
    }

    override fun parse(input: String): Result<RootDocument> {
        return try {
            val rootDocument = json.decodeFromString<RootDocument>(input)
            Result.success(rootDocument)
        } catch (e: SerializationException) {
            Result.failure("Failed to parse input: ${e.message}")
        }
    }
}
```

### `AnalyserVisitor.kt`
Analyzes **Figma component types** and verifies **semantic consistency**.

```kotlin
class AnalyserVisitor : Visitor<AnalyserResult> {
    override fun visit(instance: Instance, additionalData: AdditionalData?): AnalyserResult {
        val componentType = ComponentType.findTaggedComponentType(instance.name)

        if (componentType == ComponentType.LIST) {
            componentType.additionalData = ComponentType.findListDensity(instance.name)
        }

        instance.componentType = componentType
        return AnalyserResult(instance.componentType)
    }
}
```

### `ComponentType.kt`
Defines supported **Figma component types** and their mappings to **Server-Driven UI**.

```kotlin
enum class ComponentType(
    val isTag: Boolean,
    val isM3Tag: Boolean,
    val tag: String? = null
) {
    SCREEN_FRAME(false, false),
    TEXT(false, false),
    ROW(true, false, "row"),
    COLUMN(true, false, "column"),
    BUTTON(true, true, "button"),
    CHECKBOX(true, true, "checkbox"),
    TOP_BAR(true, true, "top-app-bar"),
    BOTTOM_BAR(true, true, "bottom-app-bar");

    companion object {
        fun findTaggedComponentType(componentName: String): ComponentType {
            val match = Regex("\\[(.*?)]").find(componentName) ?: return UNTAGGED
            val (valueMatched) = match.destructured
            return entries.find { it.tag != null && valueMatched.startsWith(it.tag) } ?: UNKNOWN
        }
    }
}
```

---

## 📌 Conversion to Server-Driven UI

### `FigmaComposeConverter.kt`
Processes the parsed Figma data and converts it into **Server-Driven UI** format.

```kotlin
class FigmaComposeConverter(
    private val parser: Parser = FigmaGsonParser(),
    private val analyser: Visitor<AnalyserResult> = AnalyserVisitor()
) : Converter {
    override fun convert(input: String): RootDocument? {
        val parserResult = parser.parse(input)
        if (parserResult.hasError) return null

        val componentRoot = parserResult.data as RootDocument
        val analyserResult = componentRoot.accept(analyser)

        return if (analyserResult.errorMessages.isNullOrEmpty()) componentRoot else null
    }
}
```

---

## 🛠 How It Works

### 1️⃣ Fetching Figma Data

1. **API Request**: Retrieve Figma design data using `FigmaAPIClient`.
2. **Parsing JSON**: Convert Figma API JSON into a structured `RootDocument`.

```kotlin
val apiClient = ServiceCreator(APIClient.BASE_URL).createAPIClient()
val figmaClient = FigmaAPIClient(apiClient)

val response = figmaClient.loadFromApi(figmaFileId, apiToken)
```

### 2️⃣ Analyzing Components

- Identifies **Figma component types**.
- Checks **semantic validity** of component structures.

```kotlin
val analyser = AnalyserVisitor()
val analysisResult = rootDocument.accept(analyser)
```

### 3️⃣ Converting to JSON

- Converts Figma components into **Server-Driven UI JSON format**.
- Supports **Buttons, Columns, Rows, and Layouts**.

```kotlin
val converter = FigmaComposeConverter()
val jsonOutput = converter.convert(figmaJson)
```

---


