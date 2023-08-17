package com.weather.task7_3notebook.data.repository

import com.weather.task7_3notebook.domain.model.City
import com.weather.task7_3notebook.domain.model.Contact
import com.weather.task7_3notebook.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor() : SearchRepository {
    override fun getResultSearchContact(searchValue: String?, listContact: List<Contact>): List<Contact> {
        return listContact.filter {
            it.name.contains(
                searchValue ?: "",
                ignoreCase = true
            )
        }
    }

    override fun getResultSearchCity(searchValue: String?, listCity: List<City>): List<City> {
        return listCity.filter {
            it.nameCity.contains(
                searchValue ?: "",
                ignoreCase = true
            )
        }
    }
}
