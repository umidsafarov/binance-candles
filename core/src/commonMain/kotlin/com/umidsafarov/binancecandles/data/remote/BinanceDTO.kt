package com.umidsafarov.binancecandles.data.remote

import com.umidsafarov.binancecandles.domain.entity.Kline
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long

//enum class Interval { m1, m3, m5, m15, m30, h1, h2, h4, h6, h8, h12, d1, d3, w1, M1 };

data class KlineResponse(
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double,
    val volume: Double,
    val timeOpen: Long,
    val timeClose: Long,
)

fun jsonArrayToKlineResponse(jsonArray: JsonArray): KlineResponse {
    return KlineResponse(
        open = jsonArray[1].jsonPrimitive.double,
        close = jsonArray[4].jsonPrimitive.double,
        high = jsonArray[2].jsonPrimitive.double,
        low = jsonArray[3].jsonPrimitive.double,
        volume = jsonArray[5].jsonPrimitive.double,
        timeOpen = jsonArray[0].jsonPrimitive.long,
        timeClose = jsonArray[6].jsonPrimitive.long,
    )
}

fun KlineResponse.toEntity() = Kline(
    open = open,
    close = close,
    high = high,
    low = low,
    volume = volume,
    timeOpen = timeOpen,
    timeClose = timeClose
)
