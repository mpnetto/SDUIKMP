package org.sacada.figma2sdui

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.sacada.figma2sdui.client.APIClient
import org.sacada.figma2sdui.client.FigmaAPIClient
import org.sacada.figma2sdui.client.ServiceCreator
import org.sacada.figma2sdui.converter.FigmaComposeConverter
import org.sacada.figma2sdui.data.Result
import org.sacada.figma2sdui.data.nodes.RootDocument

suspend fun fetchFigmaData(
    apiKey: String,
    fileKey: String,
): RootDocument? {
    val serviceCreator = ServiceCreator(APIClient.BASE_URL)
    val apiClient = serviceCreator.createAPIClient()
    val figmaApiClient = FigmaAPIClient(apiClient)

    val figmaFileResult: Result<String> = figmaApiClient.loadFromApi(fileKey, apiKey)

    if (figmaFileResult.hasError) {
        println("File client error: ${figmaFileResult.errorMessage}")
        return null
    }

    val converter = FigmaComposeConverter()

    return withContext(Dispatchers.Default) {
        converter.convert(figmaFileResult.data!!)
    }
}
