package com.weather.task7_3notebook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.task7_3notebook.model.City
import com.weather.task7_3notebook.model.Contact
import com.weather.task7_3notebook.repository.CityRepository
import com.weather.task7_3notebook.repository.SearchRepository
import com.weather.task7_3notebook.utils.SingleLiveEvent

class MainViewModel : ViewModel() {

    private val searchRepository = SearchRepository()
    private val cityRepository = CityRepository()

    private val _contactLiveData = MutableLiveData<List<Contact>>(emptyList())
    private val _cityLiveData =
        MutableLiveData<List<City>>(cityRepository.createDefaultListCities())
    private val _filterLiveData = SingleLiveEvent<List<Contact>>()

    // для получения значения из вне и для подписки
    val contactLiveData: LiveData<List<Contact>>
        get() = _contactLiveData

    val filterListLiveData: LiveData<List<Contact>>
        get() = _filterLiveData

    val cityLiveData: LiveData<List<City>>
        get() = _cityLiveData

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

    fun addCity(city: City) {
        val newList = _cityLiveData.value?.plus(city) ?: listOf(city)
        updateCityLiveData(newList)
    }

    fun removeCity(city: City) {
        val newList = _cityLiveData.value?.filter {
            city != it
        } ?: emptyList()
        updateCityLiveData(newList)
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

    private fun updateCityLiveData(newValue: List<City>) {
        _cityLiveData.value = newValue
    }
}
