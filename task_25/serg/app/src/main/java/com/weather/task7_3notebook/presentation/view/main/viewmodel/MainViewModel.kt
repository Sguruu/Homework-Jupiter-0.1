package com.weather.task7_3notebook.presentation.view.main.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _searchFlow = MutableSharedFlow<String>(replay = 1)

    val searchFlow: Flow<String> = _searchFlow

    suspend fun updateSearchFlow(value: String) {
        _searchFlow.emit(value)
    }
}
