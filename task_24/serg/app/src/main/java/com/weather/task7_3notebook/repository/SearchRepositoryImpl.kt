package com.weather.task7_3notebook.repository

import com.weather.task7_3notebook.model.Contact
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor() : SearchRepository {
    override fun getResultSearch(searchValue: String?, listContact: List<Contact>): List<Contact> {
        return listContact.filter {
            it.name.contains(
                searchValue ?: "",
                ignoreCase = true
            )
        }
    }
}
