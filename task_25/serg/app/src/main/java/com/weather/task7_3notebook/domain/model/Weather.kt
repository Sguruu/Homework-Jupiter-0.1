package com.weather.task7_3notebook.domain.model

data class Weather(
    val tempMin: Double,
    val tempMax: Double,
    val descriptionWeather: String
) {
    /**
     * Получить среднюю температуру
     */
    fun getAverageTemperature(): Double =
        (tempMin + tempMax) / 2
}
