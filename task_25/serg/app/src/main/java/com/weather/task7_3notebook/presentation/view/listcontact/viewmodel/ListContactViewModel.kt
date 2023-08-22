package com.weather.task7_3notebook.presentation.view.listcontact.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.task7_3notebook.domain.interactor.ContactInteractor
import com.weather.task7_3notebook.domain.model.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListContactViewModel @Inject constructor(
    private val contactInteractor: ContactInteractor
) : ViewModel() {

    private val _filterContactsFlow = MutableSharedFlow<List<Contact>>(replay = 1)

    private val _contactsFlow: StateFlow<List<Contact>> =
        contactInteractor.getAllContact()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val filterContactsFlow: Flow<List<Contact>> = _filterContactsFlow

    val contactsFlow: Flow<List<Contact>> = contactInteractor.getAllContact()

    fun removeContact(contact: Contact) {
        viewModelScope.launch {
            contactInteractor.deleteContact(contact)
        }
    }

    fun search(searchValue: String?) {
        viewModelScope.launch {
            _contactsFlow.value.let {
                val filterList = contactInteractor.getResultSearch(searchValue, it)
                updateFilterFlow(filterList)
            }
        }
    }

    private suspend fun updateFilterFlow(newValue: List<Contact>) {
        _filterContactsFlow.emit(newValue)
    }
}
