package com.umidsafarov.binancecandles.data.remote

import com.umidsafarov.binancecandles.domain.entity.Kline
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlin.native.concurrent.ThreadLocal


@ThreadLocal
val globalJson = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

class BinanceApi(private val endpoint: String) {
    companion object {
        private const val KLINE_ENDPOINT = "/api/v3/klines"
    }

    private var key = ""
    private var secret = ""

    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(globalJson)
        }
    }

    suspend fun authorize(key: String, secret: String): Boolean {
        //todo check authorization
        this.key = key
        this.secret = secret
        return true
    }

    suspend fun getKlines(symbol: String, interval: String, lastSync: Long?): List<Kline> {
        return httpClient.get<List<JsonArray>>(endpoint + KLINE_ENDPOINT) {
            method = HttpMethod.Get
            parameter("symbol", symbol)
            parameter("interval", interval)
            if (lastSync != null)
                parameter("startTime", lastSync)
            headers {
                header("apiKey", key)
                header("secretKey", secret)
            }
        }.map { jsonArrayToKlineResponse(it).toEntity() }
    }
}