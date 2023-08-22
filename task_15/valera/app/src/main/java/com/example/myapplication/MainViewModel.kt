package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val repository = MainRepository()
    private val _friendLiveData = MutableLiveData<ArrayList<Friend>>()
    private val _filterLiveData = SingleLiveEvent<ArrayList<Friend>>()

    val friendLiveData: LiveData<ArrayList<Friend>>
        get() = _friendLiveData

    val filterListLiveData: LiveData<ArrayList<Friend>>
        get() = _filterLiveData

    fun filterFriendList(valueSearch: String?) {
        _friendLiveData.value?.let {
            val filterList = repository.filterFriendList(valueSearch, it)
            updateFilterLiveData(filterList)
        }
    }

    fun addFriendOnList(name: String, surname: String, phoneNumber: String) {
        val list = ArrayList<Friend>()
        if (_friendLiveData.value?.isNotEmpty() == true){
            val newList = _friendLiveData.value!!.plus(Friend(name, surname, phoneNumber))
            newList.let {
                it.forEach {friend ->
                    val newFriend = Friend(friend.name, friend.surname, friend.phoneNumber)
                    list.add(newFriend)
                }
            }
        } else {
            list.add(Friend(name, surname, phoneNumber))
        }
        updateFriendLiveData(list)
    }

    fun dellFriend(friend: Friend) {
        val newList = _friendLiveData.value?.filter {
            friend != it
        } as ArrayList<Friend>
        updateFriendLiveData(newList)
    }

    fun resetFilterLiveData (){
        _filterLiveData.value = ArrayList()
    }

    private fun updateFriendLiveData(newValue: ArrayList<Friend>) {
        _friendLiveData.value = newValue
    }

    private fun updateFilterLiveData(newValue: ArrayList<Friend>) {
        _filterLiveData.value = newValue
    }
}