package com.weather.task7_3notebook.domain.mapper

import com.weather.task7_3notebook.data.db.model.CityEntity
import com.weather.task7_3notebook.domain.model.City

fun City.toCityEntity(): CityEntity = CityEntity(
    city = CityEntity.City(
        nameCity = this.nameCity,
        latitude = this.latitude,
        longitude = this.longitude
    ),
    weather =
    CityEntity.Weather(
        tempMin = this.weather?.tempMin.toString(),
        tempMax = this.weather?.tempMax.toString(),
        descriptionWeather = this.weather?.descriptionWeather ?: ""
    )
)
