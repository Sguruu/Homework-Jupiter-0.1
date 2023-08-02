package com.weather.task7_3notebook.base

import android.app.Application
import android.content.res.Resources
import com.weather.task7_3notebook.base.db.DataBase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        res = this.resources
        DataBase.init(this)
    }

    companion object {
        lateinit var res: Resources
            private set
    }
}
