package com.weather.task7_3notebook.domain.repository

import com.weather.task7_3notebook.data.network.model.ResponseWeather
import com.weather.task7_3notebook.domain.common.Result

interface WeatherRepository {
    suspend fun requestWeather(
        lat: String,
        lon: String
    ): Result<ResponseWeather>
}
