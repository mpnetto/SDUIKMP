package com.sacada.figma2sdui.parser

import com.sacada.figma2sdui.data.Result
import com.sacada.figma2sdui.data.nodes.RootDocument
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class FigmaGsonParser : Parser {
    private val json: Json = Json {
        classDiscriminator = "type"
        ignoreUnknownKeys = true
    }
    override fun parse(input: String): Result<RootDocument> {
        try {
            val rootDocument = json.decodeFromString<RootDocument>(input)
            Result.success(rootDocument)
            return Result.success(rootDocument)
        } catch (e: SerializationException) {
            return Result.failure(
                "Failed to parse input: ${e.message} "
            )
        }
    }
}
