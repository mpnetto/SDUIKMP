package com.sacada.figma2sdui.client

import com.sacada.figma2sdui.data.Result
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query

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
