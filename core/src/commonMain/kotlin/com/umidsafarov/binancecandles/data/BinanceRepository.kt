package com.umidsafarov.binancecandles.data

import com.umidsafarov.binancecandles.data.local.BinanceDatabase
import com.umidsafarov.binancecandles.data.local.DatabaseDriverFactory
import com.umidsafarov.binancecandles.data.remote.BinanceApi
import com.umidsafarov.binancecandles.domain.entity.Kline
import kotlin.native.concurrent.ThreadLocal

class BinanceRepository(databaseDriverFactory: DatabaseDriverFactory, binanceEndpoint: String) {
    private val database = BinanceDatabase(databaseDriverFactory)
    private val api = BinanceApi(binanceEndpoint)

    @Throws(Exception::class)
    suspend fun getKlines(
        forceReload: Boolean,
        symbol: String,
        interval: String,
        lastSync: Long?
    ): List<Kline> {
        val cached = database.getAllKlines()
        if (cached.isNotEmpty() && !forceReload)
            return cached
        else {
            api.getKlines(symbol, interval, lastSync).also {
                database.createKlines(it)
            }
            return database.getAllKlines()
        }
    }

    @Throws(Exception::class)
    suspend fun authorize(key: String, secret: String): Boolean {
        return api.authorize(key, secret)
    }
}