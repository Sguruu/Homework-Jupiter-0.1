package com.weather.task7_3notebook.repository

import com.weather.task7_3notebook.base.Result
import com.weather.task7_3notebook.model.ResponseWeather

interface WeatherRepository {
    suspend fun requestWeather(
        lat: String,
        lon: String
    ): Result<ResponseWeather>
}
