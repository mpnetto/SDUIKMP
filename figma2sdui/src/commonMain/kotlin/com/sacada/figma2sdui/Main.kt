package com.sacada.figma2sdui

import com.sacada.figma2sdui.client.APIClient
import com.sacada.figma2sdui.client.FigmaAPIClient
import com.sacada.figma2sdui.client.ServiceCreator
import com.sacada.figma2sdui.converter.FigmaComposeConverter
import com.sacada.figma2sdui.data.Result
import com.sacada.figma2sdui.data.nodes.RootDocument
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun fetchFigmaData(apiKey: String, fileKey: String, onResult: (RootDocument?) -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        val serviceCreator = ServiceCreator(APIClient.BASE_URL)
        val apiClient = serviceCreator.createAPIClient()
        val figmaApiClient = FigmaAPIClient(apiClient)

        val figmaFileResult: Result<String> = figmaApiClient.loadFromApi(fileKey, apiKey)

        if (figmaFileResult.hasError) {
            println("File client error: ${figmaFileResult.errorMessage}")
            onResult(null)
            return@launch
        }

        val converter = FigmaComposeConverter()

        val result = withContext(Dispatchers.Default) {
            converter.convert(figmaFileResult.data!!)
        }

        onResult(result)
    }
}
