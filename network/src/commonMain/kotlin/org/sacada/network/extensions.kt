package org.sacada.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

suspend inline fun <reified T> HttpClient.getTyped(
    url: String,
    headers: Map<String, String> = emptyMap(),
): NetworkResult<T> =
    runCatching {
        get(url) { headers.forEach { (key, value) -> header(key, value) } }.body<T>()
    }.fold(
        onSuccess = { NetworkResult.Success(it) },
        onFailure = { NetworkResult.Error(it.message ?: "Unknown error", it) },
    )

suspend inline fun <reified RES, reified REQ : Any> HttpClient.postTyped(
    url: String,
    bodyObj: REQ,
    headers: Map<String, String> = emptyMap(),
): NetworkResult<RES> =
    runCatching {
        post(url) {
            headers.forEach { (key, value) -> header(key, value) }
            contentType(ContentType.Application.Json)
            setBody(bodyObj)
        }.body<RES>()
    }.fold(
        onSuccess = { NetworkResult.Success(it) },
        onFailure = { NetworkResult.Error(it.message ?: "Unknown error", it) },
    )
