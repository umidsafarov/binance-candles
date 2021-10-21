package com.umidsafarov.binancecandles.data.local

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory(schema:SqlDriver.Schema, dbName:String) {
    fun createDriver(): SqlDriver
}
