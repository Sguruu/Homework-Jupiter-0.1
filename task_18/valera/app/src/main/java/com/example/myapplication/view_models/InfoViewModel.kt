package com.example.myapplication.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.ResponseRequestText
import com.example.myapplication.repository.MainRepository

class InfoViewModel : ViewModel() {
    private val repository = MainRepository()
    private val _weatherLiveData = MutableLiveData<ResponseRequestText?>()
    private val _positionLiveData = MutableLiveData<Int?>()
    val weatherLiveData: LiveData<ResponseRequestText?>
        get() = _weatherLiveData

    val positionLiveData: LiveData<Int?>
        get() = _positionLiveData

    private fun updateWeatherLiveData(newValue: ResponseRequestText) {
        _weatherLiveData.postValue(newValue)
    }

    fun updatePositionLiveData(newValue: Int?) {
        _positionLiveData.value = newValue
    }

    fun requestWeather(latitude: String, longitude: String) {
        Thread {
            repository.requestWeather(latitude, longitude) {
                it?.let {
                    updateWeatherLiveData(it)
                }
            }
        }.start()
    }
}
