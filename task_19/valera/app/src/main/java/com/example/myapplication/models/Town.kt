package com.example.myapplication.models

data class Town(
    var name: String,
    var latitude: Double,
    var longitude: Double,
    var weather: Weather? = null
)
