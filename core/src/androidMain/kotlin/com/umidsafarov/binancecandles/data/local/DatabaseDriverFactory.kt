package com.umidsafarov.binancecandles.data.local

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.umidsafarov.binancecandles.LaunchesListApplication

actual class DatabaseDriverFactory actual constructor(schema:SqlDriver.Schema, dbName:String) {
    private val mSchema = schema
    private val mDbName = dbName

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(mSchema, LaunchesListApplication.appContext, mDbName)
    }
}