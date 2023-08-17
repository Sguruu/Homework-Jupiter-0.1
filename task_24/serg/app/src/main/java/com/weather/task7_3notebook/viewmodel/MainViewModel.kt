package com.weather.task7_3notebook.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.task7_3notebook.base.Result
import com.weather.task7_3notebook.model.City
import com.weather.task7_3notebook.model.Contact
import com.weather.task7_3notebook.model.ResponseWeather
import com.weather.task7_3notebook.model.StateStatusSaveCity
import com.weather.task7_3notebook.model.Weather
import com.weather.task7_3notebook.repository.BaseRepository
import com.weather.task7_3notebook.repository.CityRepository
import com.weather.task7_3notebook.repository.ContactRepository
import com.weather.task7_3notebook.repository.SearchRepository
import com.weather.task7_3notebook.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchRepositoryImpl: SearchRepository,
    private val cityRepositoryImpl: CityRepository,
    private val weatherRepositoryImpl: WeatherRepository,
    private val baseRepositoryImpl: BaseRepository,
    private val contactRepositoryImpl: ContactRepository
) : ViewModel() {

    private val _contactsFlow = MutableStateFlow<List<Contact>>(emptyList())
    private val _citiesFlow = MutableStateFlow<List<City>>(emptyList())

    private val _filterContactsFlow = MutableSharedFlow<List<Contact>>(replay = 1)

    private val _stateStatusSaveCityFlow = MutableSharedFlow<StateStatusSaveCity>(replay = 1)

    val contactsFlow: Flow<List<Contact>> = _contactsFlow

    val citiesFlow: Flow<List<City>> = _citiesFlow

    val filterContactsFlow: Flow<List<Contact>> = _filterContactsFlow

    val stateStatusSaveCityFlow: Flow<StateStatusSaveCity> = _stateStatusSaveCityFlow

    init {
        viewModelScope.launch {
            updateCityLiveData(cityRepositoryImpl.getAllCity())
            updateContactLiveData(contactRepositoryImpl.getAllContact())
        }
    }

    fun updateWeatherDataCities(context: Context) {
        if (baseRepositoryImpl.checkIsInternet(context)) {
            viewModelScope.launch {
                val newList: MutableList<City> = _citiesFlow.value.toMutableList()
                val jobsArray = arrayListOf<Job>()
                newList.let {
                    // создаю массив jobs
                    _citiesFlow.value.forEachIndexed { index, city ->
                        jobsArray.add(
                            launch {
                                requestWeather(city.latitude, city.longitude) { weather ->
                                    newList[index] = city.copy(weather = weather)
                                }
                            }
                        )
                    }
                    // дожидаюсь завершение всех jobs
                    jobsArray.joinAll()
                    updateCityLiveData(newList)
                    cityRepositoryImpl.updateCites(newList)
                }
            }
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            contactRepositoryImpl.addContact(contact)
            val newList = _contactsFlow.value.plus(contact) ?: listOf(contact)
            updateContactLiveData(newList)
        }
    }

    fun removeContact(contact: Contact) {
        viewModelScope.launch {
            val newList = _contactsFlow.value.filter {
                contact != it
            }
            updateContactLiveData(newList)
            contactRepositoryImpl.deleteContact(contact)
        }
    }

    fun addCity(city: City, context: Context) {
        viewModelScope.launch {
            when (baseRepositoryImpl.checkIsInternet(context)) {
                true -> {
                    requestWeather(city.latitude, city.longitude) {
                        val newCity = city.copy(weather = it)
                        saveCity(newCity)
                        updateStateStatusSaveCity(StateStatusSaveCity.Success)
                    }
                }

                // если интернета нет, сохраняем город без погоды
                false -> {
                    updateStateStatusSaveCity(StateStatusSaveCity.NoInternet)
                    saveCity(city.copy(latitude = "", longitude = ""))
                }
            }
        }
    }

    fun removeCity(city: City) {
        viewModelScope.launch {
            val newList = _citiesFlow.value.filter {
                city != it
            }
            cityRepositoryImpl.deleteCity(city)
            updateCityLiveData(newList)
        }
    }

    fun search(searchValue: String?) {
        viewModelScope.launch {
            _contactsFlow.value.let {
                val filterList = searchRepositoryImpl.getResultSearch(searchValue, it)
                updateFilterLiveData(filterList)
            }
        }
    }

    private suspend fun saveCity(city: City) {
        val newList = _citiesFlow.value.plus(city)
        cityRepositoryImpl.insertCity(city)
        updateCityLiveData(newList)
    }

    private suspend fun requestWeather(
        lat: String,
        lon: String,
        callback: suspend (weather: Weather) -> Unit
    ) {
        weatherRepositoryImpl.requestWeather(lat, lon).apply {
            when (this) {
                is Result.Success<ResponseWeather> -> {
                    callback.invoke(
                        Weather(
                            tempMin = this.data.main.tempMin,
                            tempMax = this.data.main.tempMax,
                            descriptionWeather = this.data.weatherCurrent[0].description
                        )
                    )
                }

                is Result.Error -> {
                    updateStateStatusSaveCity(
                        StateStatusSaveCity.Error(
                            this.errorValue.orEmpty()
                        )
                    )
                }
            }
        }
    }

    private fun updateContactLiveData(newValue: List<Contact>) {
        _contactsFlow.value = newValue
    }

    private suspend fun updateFilterLiveData(newValue: List<Contact>) {
        _filterContactsFlow.emit(newValue)
    }

    private fun updateCityLiveData(newValue: List<City>) {
        _citiesFlow.value = newValue
    }

    private suspend fun updateStateStatusSaveCity(newValue: StateStatusSaveCity) {
        _stateStatusSaveCityFlow.emit(newValue)
    }
}
