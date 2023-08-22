package com.weather.task7_3notebook.data.repository

import com.weather.task7_3notebook.data.db.dao.ContactDao
import com.weather.task7_3notebook.data.db.dao.ContactWithCityDao
import com.weather.task7_3notebook.data.db.model.ContactEntity
import com.weather.task7_3notebook.data.db.model.ContactWithCityEntity
import com.weather.task7_3notebook.domain.model.Contact
import com.weather.task7_3notebook.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val dbContact: ContactDao,
    private val dbContactWithCityDao: ContactWithCityDao
) : ContactRepository {

    override fun getAllContact(): Flow<List<ContactWithCityEntity>> {
        return dbContactWithCityDao.getAllContactWithCityDao()
    }

    override suspend fun addContact(contact: ContactEntity) {
        dbContact.insertContact(contact)
    }

    override suspend fun deleteContact(contact: Contact) {
        dbContact.deleteContact(contact.name, contact.lastName, contact.number.toString())
    }
}
