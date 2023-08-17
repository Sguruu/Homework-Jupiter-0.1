package com.weather.task7_3notebook.domain.model

data class City(
    val nameCity: String,
    /**
     * Широта
     */
    val latitude: String,
    /**
     * Долгота
     */
    val longitude: String,
    /**
     * Прогноз погоды
     */
    val weather: Weather? = null
)
