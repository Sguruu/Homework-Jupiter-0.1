package com.weather.task7_3notebook.presentation.view.addcity.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.task7_3notebook.domain.common.Result
import com.weather.task7_3notebook.domain.interactor.CityInteractor
import com.weather.task7_3notebook.domain.model.City
import com.weather.task7_3notebook.presentation.model.StateStatusSaveCity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCityViewModel @Inject constructor(
    private val cityInteractor: CityInteractor
) : ViewModel() {

    private val _stateStatusSaveCityFlow = MutableSharedFlow<StateStatusSaveCity>(replay = 1)
    val stateStatusSaveCityFlow: Flow<StateStatusSaveCity> = _stateStatusSaveCityFlow

    fun addCity(city: City, context: Context) {
        viewModelScope.launch {
            cityInteractor.addCity(city, context).apply {
                when (this) {
                    is Result.Success<City> -> {
                        updateStateStatusSaveCity(StateStatusSaveCity.Success)
                    }

                    is Result.Error.ErrorResponse -> {
                        updateStateStatusSaveCity(
                            StateStatusSaveCity.Error(
                                this.errorValue.orEmpty()
                            )
                        )
                    }

                    is Result.Error.ErrorNoInternet<*> -> {
                        updateStateStatusSaveCity(StateStatusSaveCity.NoInternet)
                    }
                }
            }
        }
    }

    private suspend fun updateStateStatusSaveCity(newValue: StateStatusSaveCity) {
        _stateStatusSaveCityFlow.emit(newValue)
    }
}
