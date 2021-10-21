package com.umidsafarov.binancecandles

import android.app.Application

class LaunchesListApplication : Application() {
    companion object {
        lateinit var appContext:Application
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}