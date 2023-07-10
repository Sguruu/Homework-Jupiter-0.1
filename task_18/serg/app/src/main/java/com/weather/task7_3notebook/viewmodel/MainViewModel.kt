package com.weather.task7_3notebook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.task7_3notebook.utils.SingleLiveEvent
import com.weather.task7_3notebook.model.Contact
import com.weather.task7_3notebook.repository.SearchRepository

class MainViewModel : ViewModel() {

    private val _contactLiveData = MutableLiveData<List<Contact>>(emptyList())
    private val _filterLiveData = SingleLiveEvent<List<Contact>>()
    private val searchRepository = SearchRepository()

    // для получения значения из вне и для подписки
    val contactLiveData: LiveData<List<Contact>>
        get() = _contactLiveData

    val filterListLiveData: LiveData<List<Contact>>
        get() = _filterLiveData

    fun addContact(contact: Contact) {
        val newList = _contactLiveData.value?.plus(contact) ?: listOf(contact)
        updateContactLiveData(newList)
    }

    fun removeContact(contact: Contact) {
        val newList = _contactLiveData.value?.filter {
            contact != it
        } ?: emptyList()
        updateContactLiveData(newList)
    }

    fun search(searchValue: String?) {
        _contactLiveData.value?.let {
            val filterList = searchRepository.getResultSearch(searchValue, it)
            updateFilterLiveData(filterList)
        }
    }

    private fun updateContactLiveData(newValue: List<Contact>) {
        _contactLiveData.value = newValue
    }

    private fun updateFilterLiveData(newValue: List<Contact>) {
        _filterLiveData.value = newValue
    }
}
