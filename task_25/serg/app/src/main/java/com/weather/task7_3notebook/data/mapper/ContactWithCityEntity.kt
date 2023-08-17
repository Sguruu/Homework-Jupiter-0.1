package com.weather.task7_3notebook.data.mapper

import com.weather.task7_3notebook.data.db.model.ContactWithCityEntity
import com.weather.task7_3notebook.domain.model.City
import com.weather.task7_3notebook.domain.model.Contact
import com.weather.task7_3notebook.domain.model.Weather

fun ContactWithCityEntity.toContact(): Contact = Contact(
    name = this.contact.contact.name,
    lastName = this.contact.contact.lastName,
    number = this.contact.contact.number.toLongOrNull() ?: 0,
    city = this.city?.city?.let {
        City(
            nameCity = this.city.city.nameCity,
            latitude = it.latitude,
            longitude = this.city.city.longitude,
            weather = Weather(
                tempMin = this.city.weather.tempMin.toDoubleOrNull() ?: 0.0,
                tempMax = this.city.weather.tempMax.toDoubleOrNull() ?: 0.0,
                descriptionWeather = this.city.weather.descriptionWeather
            )
        )
    }

)
