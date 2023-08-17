package com.weather.task7_3notebook.presentation.view.addcontact.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.task7_3notebook.domain.interactor.CityInteractor
import com.weather.task7_3notebook.domain.interactor.ContactInteractor
import com.weather.task7_3notebook.domain.model.City
import com.weather.task7_3notebook.domain.model.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val contactInteractor: ContactInteractor,
    private val cityInteractor: CityInteractor
) : ViewModel() {
    private val _citiesFlow = MutableStateFlow<List<City>>(emptyList())
    val citiesFlow: Flow<List<City>> = _citiesFlow

    init {
        viewModelScope.launch {
            cityInteractor.getAllCity().collect {
                updateCityLiveData(it)
            }
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            contactInteractor.addContact(contact)
        }
    }

    private fun updateCityLiveData(newValue: List<City>) {
        _citiesFlow.value = newValue
    }
}
