package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.Town

class TownViewModel : ViewModel() {
//    private val repository = MainRepository()
    private val _townLiveData = MutableLiveData<ArrayList<Town>>()
//    private val _filterLiveData = SingleLiveEvent<ArrayList<Friend>>()

    val townLiveData: LiveData<ArrayList<Town>>
        get() = _townLiveData

//    val filterListLiveData: LiveData<ArrayList<Friend>>
//        get() = _filterLiveData

//    fun filterFriendList(valueSearch: String?) {
//        _townLiveData.value?.let {
//            val filterList = repository.filterFriendList(valueSearch, it)
//            updateFilterLiveData(filterList)
//        }
//    }

    fun addTownOnList(name: String, latitude: Double, longitude: Double) {
        val list = ArrayList<Town>()
        if (_townLiveData.value?.isNotEmpty() == true){
            val newList = _townLiveData.value!!.plus(Town(name, latitude, longitude))
            newList.let {
                it.forEach {town ->
                    val newTown = Town(town.name, town.latitude, town.longitude)
                    list.add(newTown)
                }
            }
        } else {
            list.add(Town(name, latitude, longitude))
        }
        updateTownLiveData(list)
    }

    fun dellTown(town: Town) {
        val newtowns = _townLiveData.value?.filter {
            town != it
        } as ArrayList<Town>
        updateTownLiveData(newtowns)
    }

//    fun resetFilterLiveData (){
//        _filterLiveData.value = ArrayList()
//    }

    private fun updateTownLiveData(newValue: ArrayList<Town>) {
        _townLiveData.value = newValue
    }

//    private fun updateFilterLiveData(newValue: ArrayList<Friend>) {
//        _filterLiveData.value = newValue
//    }
}