package com.weather.task7_3notebook.repository

import com.weather.task7_3notebook.model.Contact

interface ContactRepository {
    suspend fun getAllContact(): List<Contact>
    suspend fun addContact(contact: Contact)
    suspend fun deleteContact(contact: Contact)
}
