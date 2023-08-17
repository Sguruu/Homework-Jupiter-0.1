package com.weather.task7_3notebook.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseErrorWeather(
    @Json(name = "cod")
    val cod: String,
    @Json(name = "message")
    val message: String
)
