package com.weather.task7_3notebook.base

import android.app.Application
import android.content.res.Resources
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        res = this.resources
    }

    companion object {
        lateinit var res: Resources
            private set
    }
}
