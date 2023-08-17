package com.weather.task7_3notebook.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestWeather(
    @Json(name = "temp_min")
    val tempMin: Double,
    @Json(name = "temp_max")
    val tempMax: Double,
    @Json(name = "description")
    val descriptionWeather: String
)
