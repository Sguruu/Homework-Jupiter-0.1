package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.models.Friend
import com.example.myapplication.models.Main
import com.example.myapplication.models.ResponseRequestText
import com.example.myapplication.models.Town
import com.example.myapplication.network.Network
import org.json.JSONException
import org.json.JSONObject


class MainRepository {
    var checkRequestIsSuccessful = true

    fun filterFriendList(valueSearch: String?, friendList: ArrayList<Friend>): ArrayList<Friend> {
        val filterFriendList = ArrayList<Friend>()
        friendList.filter {
            it.name.contains(valueSearch ?: "", ignoreCase = true) ||
                    it.surname.contains(valueSearch ?: "", ignoreCase = true) ||
                    it.phoneNumber.contains(valueSearch ?: "", ignoreCase = true)
        }
            .let {
                it.forEach { friend ->
                    val newFriend =
                        Friend(friend.name, friend.surname, friend.phoneNumber, friend.town)
                    filterFriendList.add(newFriend)
                }
            }
        return filterFriendList
    }

    fun filterTowns(valueSearch: String?, towns: ArrayList<Town>): ArrayList<Town> {
        val filterTowns = ArrayList<Town>()
        towns.filter {
            it.name.contains(valueSearch ?: "", ignoreCase = true)
        }
            .let {
                it.forEach { town ->
                    val newTown = Town(town.name, town.latitude, town.longitude, town.weather)
                    filterTowns.add(newTown)
                }
            }
        return filterTowns
    }

    fun requestWeather(
        latitude: String,
        longitude: String,
        callback: (responseRequestText: ResponseRequestText?) -> Unit
    ) {
        try {
            val lat = latitude.replace('.', ',')
            val lon = longitude.replace('.', ',')
            val response = Network.getWeatherCall(lat, lon)
                .execute()
            Log.d("MyTest", "Успех запроса = ${response.isSuccessful}")
            checkRequestIsSuccessful = response.isSuccessful
            val requestText = response.body?.string().orEmpty()
            val responseRequestText = parseMovieResponse(requestText)
            if (responseRequestText != null){
                callback.invoke(responseRequestText)
            } else {
                val withoutResponseRequestText =  ResponseRequestText(null)
                callback.invoke(withoutResponseRequestText)
            }
        } catch (e: IndexOutOfBoundsException) {
            Log.d("MyTest", "Ошибка запроса погоды")
        }

    }

    private fun parseMovieResponse(responseBody: String): ResponseRequestText? {
        return try {
            val jsonObject = JSONObject(responseBody)
            val weatherArray = jsonObject.getJSONArray("weather")
            val description = weatherArray.getJSONObject(0).getString("description")
            val temp = jsonObject.getJSONObject("main").getDouble("temp")
            val humidity = jsonObject.getJSONObject("main").getInt("humidity")
            ResponseRequestText(Main(temp, description, humidity))
        } catch (e: JSONException) {
            Log.d("MyTest", "Ошибка ответа сервера")
            null
        }
    }
}