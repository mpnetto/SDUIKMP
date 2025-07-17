package org.sacada.network

import io.ktor.client.HttpClient
import io.ktor.http.URLBuilder
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom

class ApiClient(
    val baseUrl: String,
    val httpClient: HttpClient = createHttpClient(),
) {
    suspend inline fun <reified T> get(
        path: String,
        headers: Map<String, String> = emptyMap(),
    ): NetworkResult<T> {
        val url =
            buildUrl(baseUrl, path)

        return httpClient.getTyped(url.toString(), headers)
    }

    suspend inline fun <reified RES, reified REQ : Any> post(
        path: String,
        body: REQ,
        headers: Map<String, String> = emptyMap(),
    ): NetworkResult<RES> = httpClient.postTyped(buildUrl(baseUrl, path), body, headers)

    fun buildUrl(
        base: String,
        path: String,
    ): String =
        URLBuilder()
            .takeFrom(base)
            .apply {
                appendPathSegments(path.trim('/').split('/'))
            }.buildString()
}
