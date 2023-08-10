package com.weather.task7_3notebook.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.weather.task7_3notebook.utils.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val searchRepository = SearchRepository()
    private val cityRepository = CityRepository()
    private val weatherRepository = WeatherRepository()
    private val baseRepository = BaseRepository()
    private val contactRepository = ContactRepository()

    private val _contactLiveData = MutableLiveData<List<Contact>>(emptyList())
    private val _cityLiveData = MutableLiveData<List<City>>(emptyList())
    private val _filterLiveData = SingleLiveEvent<List<Contact>>()
    private val _stateStatusSaveCity =
        SingleLiveEvent<StateStatusSaveCity>()

    // для получения значения из вне и для подписки
    val contactLiveData: LiveData<List<Contact>>
        get() = _contactLiveData

    val filterListLiveData: LiveData<List<Contact>>
        get() = _filterLiveData

    val cityListLiveData: LiveData<List<City>>
        get() = _cityLiveData

    val stateStatusSaveCity: LiveData<StateStatusSaveCity>
        get() = _stateStatusSaveCity

    init {
        viewModelScope.launch {
            updateCityLiveData(cityRepository.getAllCity())
            updateContactLiveData(contactRepository.getAllContact())
        }
    }

    fun updateWeatherDataCities(context: Context) {
        if (baseRepository.checkIsInternet(context)) {
            viewModelScope.launch {
                val newList: MutableList<City>? = cityListLiveData.value?.toMutableList()
                val jobsArray = arrayListOf<Job>()
                newList?.let {
                    // создаю массив jobs
                    cityListLiveData.value?.forEachIndexed { index, city ->
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
                    cityRepository.updateCites(newList)
                }
            }
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            contactRepository.addContact(contact)
            val newList = _contactLiveData.value?.plus(contact) ?: listOf(contact)
            updateContactLiveData(newList)
        }
    }

    fun removeContact(contact: Contact) {
        viewModelScope.launch {
            val newList = _contactLiveData.value?.filter {
                contact != it
            } ?: emptyList()
            updateContactLiveData(newList)
            contactRepository.deleteContact(contact)
        }
    }

    fun addCity(city: City, context: Context) {
        viewModelScope.launch {
            when (baseRepository.checkIsInternet(context)) {
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
            val newList = _cityLiveData.value?.filter {
                city != it
            } ?: emptyList()
            cityRepository.deleteCity(city)
            updateCityLiveData(newList)
        }
    }

    fun search(searchValue: String?) {
        _contactLiveData.value?.let {
            val filterList = searchRepository.getResultSearch(searchValue, it)
            updateFilterLiveData(filterList)
        }
    }

    private suspend fun saveCity(city: City) {
        val newList = _cityLiveData.value?.plus(city) ?: listOf(city)
        cityRepository.insertCity(city)
        updateCityLiveData(newList)
    }

    private suspend fun requestWeather(
        lat: String,
        lon: String,
        callback: suspend (weather: Weather) -> Unit
    ) {
        weatherRepository.requestWeather(lat, lon).apply {
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
        _contactLiveData.value = newValue
    }

    private fun updateFilterLiveData(newValue: List<Contact>) {
        _filterLiveData.value = newValue
    }

    private fun updateCityLiveData(newValue: List<City>) {
        _cityLiveData.value = newValue
    }

    private fun updateStateStatusSaveCity(newValue: StateStatusSaveCity) {
        _stateStatusSaveCity.value = newValue
    }
}
