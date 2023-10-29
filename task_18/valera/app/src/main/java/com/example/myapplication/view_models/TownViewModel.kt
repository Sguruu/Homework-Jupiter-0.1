package com.example.myapplication.view_models

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.SingleLiveEvent
import com.example.myapplication.models.Town
import com.example.myapplication.models.Weather
import com.example.myapplication.repository.MainRepository

class TownViewModel : ViewModel() {
    private val repository = MainRepository()
    private val _townLiveData = MutableLiveData<ArrayList<Town>>()
    private val _filterLiveData = SingleLiveEvent<ArrayList<Town>>()

    val townLiveData: LiveData<ArrayList<Town>>
        get() = _townLiveData

    val filterListLiveData: LiveData<ArrayList<Town>>
        get() = _filterLiveData

    fun filterTowns(valueSearch: String?) {
        _townLiveData.value?.let {
            val filterList = repository.filterTowns(valueSearch, it)
            updateFilterLiveData(filterList)
        }
    }

    fun createDefaultList() {
        val list = ArrayList<Town>()
        list.add(
            Town(
                "Самара", 53.2415, 50.2212, null
            )
        )
        updateTownLiveData(list)
    }

    fun addTownOnList(name: String, latitude: Double, longitude: Double, weather: Weather?) {
        val list = ArrayList<Town>()
        val newList = _townLiveData.value!!.plus(Town(name, latitude, longitude, weather))
        newList.let {
            it.forEach { town ->
                val newTown = Town(town.name, town.latitude, town.longitude, town.weather)
                list.add(newTown)
            }
        }
        updateTownLiveData(list)
    }

    fun dellTown(town: Town) {
        val newTowns = _townLiveData.value?.filter {
            town != it
        } as ArrayList<Town>
        updateTownLiveData(newTowns)
    }

    fun resetFilterLiveData() {
        _filterLiveData.value = ArrayList()
    }

    private fun updateTownLiveData(newValue: ArrayList<Town>) {
        _townLiveData.value = newValue
    }

    private fun updateFilterLiveData(newValue: ArrayList<Town>) {
        _filterLiveData.value = newValue
    }

    fun checkIsInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            networkCapabilities != null && networkCapabilities
                .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
    }
}
