package com.weather.task7_3notebook.data.mapper

import com.weather.task7_3notebook.data.network.model.ResponseWeather
import com.weather.task7_3notebook.domain.model.Weather

fun ResponseWeather.toWeather(): Weather {
    return Weather(
        tempMin = this.main.tempMin,
        tempMax = this.main.tempMax,
        descriptionWeather = this.weatherCurrent[0].description
    )
}
