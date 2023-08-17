package com.weather.task7_3notebook.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// эта аннотация позволитя нам преобразовать JSON объекты в объекты класса ResponseWeather и обратно
// параметр generateAdapter показывает нам что нужно сгенерировать класс который быдет заниматся
// сериализацией и десериализация, в данном случае каждое значение будет искаться по имени поля
@JsonClass(generateAdapter = true)
data class ResponseWeather(
    @Json(name = "main")
    val main: Main,
    @Json(name = "weather")
    val weatherCurrent: List<WeatherCurrent>,
    @Json(name = "country")
    val country: String? = "Russia"
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp_min")
    val tempMin: Double,
    @Json(name = "temp_max")
    val tempMax: Double
)

@JsonClass(generateAdapter = true)
data class WeatherCurrent(
    @Json(name = "description")
    val description: String
)
