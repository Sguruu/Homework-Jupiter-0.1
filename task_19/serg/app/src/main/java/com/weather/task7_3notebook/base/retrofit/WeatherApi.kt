package com.weather.task7_3notebook.base.retrofit

import com.weather.task7_3notebook.base.network.Network
import com.weather.task7_3notebook.model.ResponseWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    // https://api.openweathermap.org/data/2.5/weather?lat=46.959746&lon=142.731299&appid=a552b39b529b0402a3b40d2affee9ef4&lang=ru&units=metric
    // в параметрах указываем путь по которому нужно делать запрос
    @GET("data/2.5/weather?appid=${Network.API_KEY}&lang=${Network.LANG}&units=${Network.UNITS}")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): ResponseWeather
}
