package com.jaya.app.labreports.app

import android.app.Application
import com.jaya.app.labreports.utilities.Metar
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JayaLabReports: Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        Metar.initialize(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        INSTANCE = null
    }

    companion object {
        var INSTANCE: JayaLabReports? = null
    }
}