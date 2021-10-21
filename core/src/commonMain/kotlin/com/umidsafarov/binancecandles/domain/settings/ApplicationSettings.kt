package com.umidsafarov.binancecandles.domain.settings

import com.umidsafarov.binancecandles.core.application.ApplicationSQLDelight
import com.umidsafarov.binancecandles.data.local.DatabaseDriverFactory
import kotlin.native.concurrent.ThreadLocal

//iOS freezes singletons atm.
//todo handle singletons using on iOS
@ThreadLocal
private object LocalThreadValues {
    var userKey: String = ""
    var userSecret: String = ""
    var lastSync: Long? = null
}

class ApplicationSettings(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = ApplicationSQLDelight(databaseDriverFactory.createDriver())
    private val dbQuery = database.applicationSQLDelightQueries

    val userKey: String
        get() = LocalThreadValues.userKey

    val userSecret: String
        get() = LocalThreadValues.userSecret

    val lastSync: Long?
        get() = LocalThreadValues.lastSync

    init {
        load()
    }

    private fun load() {
        val settings = dbQuery.getSettings().executeAsOneOrNull()
        LocalThreadValues.userKey = settings?.userKey ?: ""
        LocalThreadValues.userSecret = settings?.userSecret ?: ""
        LocalThreadValues.lastSync = settings?.lastSync
    }

    fun saveUser(userKey: String, userSecret: String) {
        dbQuery.insertSettings(userKey, userSecret, lastSync).also {
            LocalThreadValues.userKey = userKey
            LocalThreadValues.userSecret = userSecret
        }
    }

    fun saveSync(lastSync: Long) {
        dbQuery.insertSettings(userKey, userSecret, lastSync).also {
            LocalThreadValues.lastSync = lastSync
        }
    }
}