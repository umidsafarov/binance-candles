package com.umidsafarov.binancecandles.data.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory actual constructor(schema: SqlDriver.Schema, dbName: String) {
    private val mSchema = schema
    private val mDbName = dbName

    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(mSchema, mDbName)
    }
}
