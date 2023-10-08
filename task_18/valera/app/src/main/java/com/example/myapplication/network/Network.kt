package com.example.myapplication.network

import android.util.Log
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

private const val API_KEY = "a552b39b529b0402a3b40d2affee9ef4"
private const val LANGUAGE = "ru"
private const val UNITS = "metric"

object Network {
    private val client = OkHttpClient()
    fun getWeatherCall (latitude: String, longitude: String): Call {
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("api.openweathermap.org")
            .addPathSegment("data")
            .addPathSegment("2.5")
            .addPathSegment("weather")
            .addQueryParameter("lon", longitude)
            .addQueryParameter("lat", latitude)
            .addQueryParameter("appid", API_KEY)
            .addQueryParameter("lang", LANGUAGE)
            .addQueryParameter("units", UNITS)
            .build()

        Log.d("MyTest", "Url $url")

        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        return client.newCall(request)
    }
}