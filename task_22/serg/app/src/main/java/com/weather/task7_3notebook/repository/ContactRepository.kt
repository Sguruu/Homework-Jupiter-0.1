package com.weather.task7_3notebook.repository

import com.weather.task7_3notebook.base.db.DataBase
import com.weather.task7_3notebook.base.db.model.ContactEntity
import com.weather.task7_3notebook.model.City
import com.weather.task7_3notebook.model.Contact
import com.weather.task7_3notebook.model.Weather

class ContactRepository {
    private val dbContact = DataBase.instance.ContactDao()
    private val dbContactWithCityDao = DataBase.instance.ContactWithCityDao()

    suspend fun getAllContact(): List<Contact> {
        return dbContactWithCityDao.getAllContactWithCityDao().map {
            Contact(
                name = it.contact.contact.name,
                lastName = it.contact.contact.lastName,
                number = it.contact.contact.number.toLongOrNull() ?: 0,
                city = City(
                    nameCity = it.city.city.nameCity,
                    latitude = it.city.city.latitude,
                    longitude = it.city.city.longitude,
                    weather = Weather(
                        tempMin = it.city.weather.tempMin.toDoubleOrNull() ?: 0.0,
                        tempMax = it.city.weather.tempMax.toDoubleOrNull() ?: 0.0,
                        descriptionWeather = it.city.weather.descriptionWeather
                    )
                )
            )
        }
    }

    suspend fun addContact(contact: Contact) {
        dbContact.insertContact(
            ContactEntity(
                contact = ContactEntity.Contact(
                    name = contact.name,
                    lastName = contact.lastName,
                    number = contact.number.toString()
                )
            )
        )
    }

    suspend fun deleteContact(contact: Contact) {
        dbContact.deleteContact(contact.name, contact.lastName, contact.number.toString())
    }
}
