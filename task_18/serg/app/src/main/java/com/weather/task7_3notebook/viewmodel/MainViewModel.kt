package com.weather.task7_3notebook.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.task7_3notebook.base.Result
import com.weather.task7_3notebook.model.City
import com.weather.task7_3notebook.model.Contact
import com.weather.task7_3notebook.model.ResponseWeather
import com.weather.task7_3notebook.model.StateStatusSaveCity
import com.weather.task7_3notebook.model.Weather
import com.weather.task7_3notebook.repository.BaseRepository
import com.weather.task7_3notebook.repository.CityRepository
import com.weather.task7_3notebook.repository.SearchRepository
import com.weather.task7_3notebook.repository.WeatherRepository
import com.weather.task7_3notebook.utils.SingleLiveEvent

class MainViewModel : ViewModel() {

    private val searchRepository = SearchRepository()
    private val cityRepository = CityRepository()
    private val weatherRepository = WeatherRepository()
    private val baseRepository = BaseRepository()
    private var currentCall: retrofit2.Call<ResponseWeather>? = null

    private val _contactLiveData = MutableLiveData<List<Contact>>(emptyList())
    private val _cityLiveData =
        MutableLiveData<List<City>>(cityRepository.createDefaultListCities())
    private val _filterLiveData = SingleLiveEvent<List<Contact>>()
    private val _stateStatusSaveCity =
        SingleLiveEvent<StateStatusSaveCity>()

    // для получения значения из вне и для подписки
    val contactLiveData: LiveData<List<Contact>>
        get() = _contactLiveData

    val filterListLiveData: LiveData<List<Contact>>
        get() = _filterLiveData

    val cityLiveData: LiveData<List<City>>
        get() = _cityLiveData

    val stateStatusSaveCity: LiveData<StateStatusSaveCity>
        get() = _stateStatusSaveCity

    init {
        cityLiveData.value?.forEachIndexed { indexOne, cityOne ->
            requestWeather(cityOne.latitude, cityOne.longitude) { weather ->
                val newList = cityLiveData.value?.mapIndexed { indexTwo, cityTwo ->
                    if (indexOne == indexTwo) {
                        return@mapIndexed cityOne.copy(weather = weather)
                    }
                    return@mapIndexed cityTwo
                }
                updateCityLiveData(newList.orEmpty())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }

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

    fun addCity(city: City, context: Context) {
        when (baseRepository.checkIsInternet(context)) {
            true -> {
                requestWeather(city.latitude, city.longitude) {
                    val newCity = city.copy(weather = it)
                    saveCity(newCity)
                }
            }
            // если интернета нет, сохраняем город без погоды
            false -> {
                updateStateStatusSaveCity(StateStatusSaveCity.NoInternet)
                saveCity(city.copy(latitude = "", longitude = ""))
            }
        }
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

    private fun saveCity(city: City) {
        val newList = _cityLiveData.value?.plus(city) ?: listOf(city)
        updateCityLiveData(newList)
    }

    private fun requestWeather(lat: String, lon: String, callback: (weather: Weather) -> Unit) {
        currentCall = weatherRepository.requestWeather(lat, lon) {
            when (it) {
                is Result.Success<Weather> -> {
                    // при успехе
                    callback.invoke(it.data)
                    updateStateStatusSaveCity(StateStatusSaveCity.Success)
                }

                is Result.Error -> {
                    // тут обрабатываем ошибку
                    updateStateStatusSaveCity(StateStatusSaveCity.Error(it.errorValue.orEmpty()))
                }
            }
            // очищаем запрос
            currentCall = null
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
