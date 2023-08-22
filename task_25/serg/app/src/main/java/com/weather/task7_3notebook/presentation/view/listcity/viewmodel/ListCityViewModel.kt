package com.weather.task7_3notebook.presentation.view.listcity.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.task7_3notebook.domain.common.Result
import com.weather.task7_3notebook.domain.interactor.CityInteractor
import com.weather.task7_3notebook.domain.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCityViewModel @Inject constructor(
    private val cityInteractor: CityInteractor
) : ViewModel() {
    private val _filterCityFlow = MutableSharedFlow<List<City>>(replay = 1)

    private val _citiesFlow = MutableStateFlow<List<City>>(emptyList())

    val filterCityFlow: Flow<List<City>> = _filterCityFlow

    val citiesFlow: Flow<List<City>> = _citiesFlow

    init {
        viewModelScope.launch {
            cityInteractor.getAllCity().collect {
                updateCityFlow(it)
            }
        }
    }

    fun updateWeatherDataCities(context: Context) {
        viewModelScope.launch {
            val newList: MutableList<City> = _citiesFlow.value.toMutableList()
            cityInteractor.updateWeatherDataCities(context, newList)?.let {
                it.apply {
                    when (this) {
                        is Result.Success -> {
                            cityInteractor.updateCites(this.data)
                        }

                        is Result.Error -> {
                            // если ошибка запроса не обновляем список
                        }
                    }
                }
            }
        }
    }

    fun removeCity(city: City) {
        viewModelScope.launch {
            val newList = _citiesFlow.value.filter {
                city != it
            }
            cityInteractor.deleteCity(city)
            updateCityFlow(newList)
        }
    }

    fun search(searchValue: String?) {
        viewModelScope.launch {
            _citiesFlow.value.let {
                val filterList = cityInteractor.getResultSearch(searchValue, it)
                updateFilterFlow(filterList)
            }
        }
    }

    private fun updateCityFlow(newValue: List<City>) {
        _citiesFlow.value = newValue
    }

    private suspend fun updateFilterFlow(newValue: List<City>) {
        _filterCityFlow.emit(newValue)
    }
}
