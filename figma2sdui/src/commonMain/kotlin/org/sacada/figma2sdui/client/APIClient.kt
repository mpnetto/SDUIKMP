package org.sacada.figma2sdui.client

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.Path

interface APIClient {

    @GET("files/{fileId}")
    suspend fun loadFromApi(
        @Path("fileId") fileId: String,
        @Header("X-Figma-Token") apiToken: String
    ): String

    companion object {
        const val BASE_URL = "https://api.figma.com/v1/"
    }
}
