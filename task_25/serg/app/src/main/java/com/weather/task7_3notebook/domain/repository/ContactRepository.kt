package com.weather.task7_3notebook.domain.repository

import com.weather.task7_3notebook.data.db.model.ContactEntity
import com.weather.task7_3notebook.data.db.model.ContactWithCityEntity
import com.weather.task7_3notebook.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun getAllContact(): Flow<List<ContactWithCityEntity>>
    suspend fun addContact(contact: ContactEntity)
    suspend fun deleteContact(contact: Contact)
}
