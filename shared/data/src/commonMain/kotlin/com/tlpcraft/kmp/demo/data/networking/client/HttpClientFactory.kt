package com.tlpcraft.kmp.demo.data.networking.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(private val engine: HttpClientEngine) {

    fun create(): HttpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = NetworkConfig.TIMEOUT_MILLIS
            connectTimeoutMillis = NetworkConfig.TIMEOUT_MILLIS
            socketTimeoutMillis = NetworkConfig.TIMEOUT_MILLIS
        }

        defaultRequest {
            url(NetworkConfig.BASE_URL)
        }
    }
}
