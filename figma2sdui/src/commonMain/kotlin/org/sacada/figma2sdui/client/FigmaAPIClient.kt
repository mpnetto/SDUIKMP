package org.sacada.figma2sdui.client

import org.sacada.figma2sdui.data.Result
import org.sacada.network.ApiClient
import org.sacada.network.NetworkResult

class FigmaAPIClient(
    private val apiClient: ApiClient,
) {
    suspend fun loadFromApi(
        fileId: String,
        apiToken: String,
    ): Result<String> {
        val networkResult =
            apiClient.get<String>(
                path = "files/$fileId",
                headers = mapOf("X-Figma-Token" to apiToken),
            )

        return when (networkResult) {
            is NetworkResult.Success -> {
                Result.success(networkResult.data)
            }

            is NetworkResult.Error -> {
                Result.failure("HTTP client send failure: ${networkResult.message}")
            }
        }
    }
}
