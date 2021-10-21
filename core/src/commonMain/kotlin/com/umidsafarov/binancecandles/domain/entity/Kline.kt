package com.umidsafarov.binancecandles.domain.entity

data class Kline(
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double,
    val volume:Double,
    val timeOpen: Long,
    val timeClose: Long,
)