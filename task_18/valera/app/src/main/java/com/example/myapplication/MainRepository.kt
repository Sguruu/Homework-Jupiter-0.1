package com.example.myapplication

import com.example.myapplication.models.Friend
import com.example.myapplication.models.Town

class MainRepository {

    fun filterFriendList(valueSearch: String?, friendList: ArrayList<Friend>) : ArrayList<Friend> {
        val filterFriendList = ArrayList <Friend>()
        friendList.filter {
            it.name.contains(valueSearch ?: "", ignoreCase = true) ||
                    it.surname.contains(valueSearch ?: "", ignoreCase = true) ||
                    it.phoneNumber.contains(valueSearch ?: "", ignoreCase = true)
        }
            .let {
                it.forEach {friend ->
                    val newFriend = Friend(friend.name, friend.surname, friend.phoneNumber, town = Town("Самара", 10.5, 15.7))
                    filterFriendList.add(newFriend)
                }
            }
        return filterFriendList
    }
}