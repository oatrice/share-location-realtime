package com.oatrice.ShareLocationRealtime.application

import android.app.Application
import timber.log.Timber

/**
 * Created by vanirut on 27/3/2560.
 */
class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

    }

    override fun onTerminate() {
        super.onTerminate()
    }
}
