package com.example.myapplication

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var filteredFriendList = ArrayList<Friend>()
    var friendList = ArrayList<Friend>()

    fun filterFriendList(valueSearch: String?) : ArrayList<Friend> {
        val filterFriendList = ArrayList <Friend>()
        friendList.filter {
            it.name.contains(valueSearch ?: "", ignoreCase = true) ||
                    it.surname.contains(valueSearch ?: "", ignoreCase = true) ||
                    it.phoneNumber.contains(valueSearch ?: "", ignoreCase = true)
        }
            .let {
                it.forEach {friend ->
                    val newFriend = Friend(friend.name, friend.surname, friend.phoneNumber)
                    filterFriendList.add(newFriend)
                }
            }
        return filterFriendList
    }

    fun addFriendOnList(name: String, surname: String, phoneNumber: String) {
        friendList.add ( Friend(name, surname, phoneNumber))
    }

    fun dellFriend(friend: Friend) {
        friendList.remove(friend)
    }




}