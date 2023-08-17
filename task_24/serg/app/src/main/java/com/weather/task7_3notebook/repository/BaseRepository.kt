package com.weather.task7_3notebook.repository

import android.content.Context

interface BaseRepository {
    /**
     * Проверка на наличие интернета
     */
    fun checkIsInternet(context: Context): Boolean
}
