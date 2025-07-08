package org.sacada.figma2sdui.client

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ServiceCreator(
    baseUrl: String,
) {
    private val httpClient =
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.BODY
            }
        }

    private val ktorfit =
        Ktorfit
            .Builder()
            .baseUrl(baseUrl)
            .httpClient(httpClient)
            .build()

    fun createAPIClient(): APIClient = ktorfit.createAPIClient()
}
