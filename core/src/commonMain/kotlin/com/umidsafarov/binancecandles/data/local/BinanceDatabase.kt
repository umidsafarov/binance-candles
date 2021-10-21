package com.umidsafarov.binancecandles.data.local

import com.umidsafarov.binancecandles.core.repository.RepositorySQLDelight
import com.umidsafarov.binancecandles.domain.entity.Kline

internal class BinanceDatabase(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = RepositorySQLDelight(databaseDriverFactory.createDriver())
    private val dbQuery = database.repositorySQLDelightQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllKlinesLaunches()
        }
    }

    internal fun getAllKlines(): List<Kline> {
        return dbQuery.selectAllKlinesInfo(::mapKlineSelecting).executeAsList()
    }

    private fun mapKlineSelecting(
        open: Double,
        close: Double,
        high: Double,
        low: Double,
        volume: Double,
        timeOpen: Long,
        timeClose: Long
    ): Kline {
        return Kline(
            open = open,
            close = close,
            high = high,
            low = low,
            volume = volume,
            timeOpen = timeOpen,
            timeClose = timeClose
        )
    }

    internal fun createKlines(klines: List<Kline>) {
        dbQuery.transaction {
            klines.forEach { kline ->
                insertLaunch(kline)
            }
        }
    }

    private fun insertLaunch(kline: Kline) {
        dbQuery.insertKline(
            open_ = kline.open,
            close = kline.close,
            high = kline.high,
            low = kline.low,
            volume = kline.volume,
            timeOpen = kline.timeOpen,
            timeClose = kline.timeClose
        )
    }
}