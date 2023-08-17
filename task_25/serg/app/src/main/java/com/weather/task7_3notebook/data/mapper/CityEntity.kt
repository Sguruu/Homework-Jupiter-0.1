package com.weather.task7_3notebook.data.mapper

import com.weather.task7_3notebook.data.db.model.CityEntity
import com.weather.task7_3notebook.domain.model.City
import com.weather.task7_3notebook.domain.model.Weather

fun CityEntity.toCity(): City = City(
    nameCity = this.city.nameCity,
    latitude = this.city.latitude,
    longitude = this.city.longitude,
    weather = Weather(
        tempMin = this.weather.tempMin.toDoubleOrNull() ?: 0.0,
        tempMax = this.weather.tempMax.toDoubleOrNull() ?: 0.0,
        descriptionWeather = this.weather.descriptionWeather
    )
)
