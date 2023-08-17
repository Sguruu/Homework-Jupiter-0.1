package com.weather.task7_3notebook.domain.interactor

import com.weather.task7_3notebook.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactInteractor {
    fun getAllContact(): Flow<List<Contact>>
    suspend fun addContact(contact: Contact)
    suspend fun deleteContact(contact: Contact)
    fun getResultSearch(searchValue: String?, listContact: List<Contact>): List<Contact>
}
