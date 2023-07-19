package com.weather.task7_3notebook.base

import android.app.Application
import android.content.res.Resources

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
