package com.example.myapplication.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.ResponseRequestText
import com.example.myapplication.repository.MainRepository

class InfoViewModel : ViewModel() {
    private val repository = MainRepository()
    private val _infoLiveData = MutableLiveData<ResponseRequestText?>()
    lateinit var thread1: Thread
//    lateinit var thread2: Thread
    val infoLiveData: LiveData<ResponseRequestText?>
        get() = _infoLiveData

    private fun updateInfoLiveData(newValue: ResponseRequestText) {
        _infoLiveData.postValue(newValue)
    }

    fun requestWeather(latitude: String, longitude: String) {
        Thread {
            repository.requestWeather(latitude, longitude) {
                it?.let {
                    updateInfoLiveData(it)
                }
            }
        }.start()
    }

//    fun setWeatherForTowns(towns: ArrayList<Town>): ArrayList<Town> {
//        towns.forEach {
//            Log.d("MyTest", "Запуск второго потока")
//            requestWeather(it.latitude.toString(), it.longitude.toString())
//            thread2 = Thread {
//                Thread.sleep(500)
//
//                Log.d("MyTest", "температура в лайв дате ${infoLiveData.value?.main?.temp}")
//                it.weather?.tempValue = infoLiveData.value?.main?.temp ?: 0.0
//                it.weather?.description = infoLiveData.value?.main?.description ?: "Нет данных"
//                it.weather?.humidityValue = infoLiveData.value?.main?.humidity ?: 0
//                Log.d("MyTest", "температура в городе ${it.weather?.tempValue}")
//            }
//            thread2.start()
//        }
//        return towns
//    }
}