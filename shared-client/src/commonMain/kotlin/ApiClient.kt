package com.example.multiplatform.shared.client

import com.example.multiplatform.shared.Message
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.http.URLProtocol

class ApiClient(engine: HttpClientEngine = httpClientEngine) {
    private val httpClient = HttpClient(engine) {
        defaultRequest {
            url.protocol = URLProtocol.HTTP // Use HTTPS for real applications!
            url.host = host
            url.port = 8080
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    suspend fun getMessage(): Message = httpClient.get {
        url {
            encodedPath = "message"
        }
    }
}
