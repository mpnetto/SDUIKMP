package com.sacada.figma2sdui.client

import com.sacada.figma2sdui.data.Result

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
