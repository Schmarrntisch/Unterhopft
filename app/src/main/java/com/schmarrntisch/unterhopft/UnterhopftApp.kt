package com.schmarrntisch.unterhopft

import android.app.Application
import timber.log.Timber

class UnterhopftApp: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}