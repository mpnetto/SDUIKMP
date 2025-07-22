package org.sacada.codegenerator

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import org.sacada.network.ApiClient
import org.sacada.network.NetworkResult

@Serializable
internal data class ChatMessage(
    val role: String,
    val content: String,
)

@Serializable
internal data class ChatCompletionRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<ChatMessage>,
)

@Serializable
internal data class ChatChoice(
    val index: Int,
    val message: ChatMessage,
    @SerialName("finish_reason") val finishReason: String? = null,
)

@Serializable
internal data class ChatCompletionResponse(
    val choices: List<ChatChoice>
)

class ChatGptClient(
    private val apiClient: ApiClient,
    private val apiKey: String,
) {
    private val rendererHelp = """
        You are part of the SDUIKMP project. The UI is rendered through renderer objects
        from the `data` module. Each renderer exposes a `Render` composable function.
        Use these when generating code. Available renderers include:
        - BoxRenderer
        - ButtonRenderer
        - BottomBarRenderer
        - CheckboxRenderer
        - ColumnRenderer
        - FloatingActionButtonRenderer
        - IconRenderer
        - IconButtonRenderer
        - ImageRenderer
        - ListRenderer
        - ListItemRenderer
        - RowRenderer
        - SwitchRenderer
        - TextRenderer
        - TextFieldRenderer
        - TopBarRenderer
        Use `RenderScreen` from `org.sacada.data.ui.screen` to render `ViewScreen` instances.
    """.trimIndent()

    suspend fun generateComposeForScreen(json: JsonObject, rendererCode: String): String? {
        val prompt = buildString {
            appendLine(rendererHelp)
            if (rendererCode.isNotBlank()) {
                appendLine()
                appendLine("Renderer source code:")
                appendLine(rendererCode)
            }
            appendLine()
            appendLine("Generate Jetpack Compose code for the following screen JSON:")
            append(json.toString())
        }
        val request = ChatCompletionRequest(messages = listOf(ChatMessage("user", prompt)))
        val result = apiClient.post<ChatCompletionResponse, ChatCompletionRequest>(
            path = "chat/completions",
            body = request,
            headers = mapOf("Authorization" to "Bearer $apiKey"),
        )
        return when (result) {
            is NetworkResult.Success -> result.data.choices.firstOrNull()?.message?.content
            is NetworkResult.Error -> null
        }
    }
}
