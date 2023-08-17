package com.weather.task7_3notebook.domain.interactor

import com.weather.task7_3notebook.data.mapper.toContact
import com.weather.task7_3notebook.domain.mapper.toContactEntity
import com.weather.task7_3notebook.domain.model.Contact
import com.weather.task7_3notebook.domain.repository.ContactRepository
import com.weather.task7_3notebook.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactInteractorImpl @Inject constructor(
    private val contactRepository: ContactRepository,
    private val searchRepository: SearchRepository
) : ContactInteractor {
    override fun getAllContact(): Flow<List<Contact>> {
        return contactRepository.getAllContact().map { listContactWithCityEntity ->
            listContactWithCityEntity.map { it.toContact() }
        }
    }

    override suspend fun addContact(contact: Contact) =
        contactRepository.addContact(contact.toContactEntity())

    override suspend fun deleteContact(contact: Contact) = contactRepository.deleteContact(contact)

    override fun getResultSearch(searchValue: String?, listContact: List<Contact>): List<Contact> =
        searchRepository.getResultSearchContact(searchValue, listContact)
}
