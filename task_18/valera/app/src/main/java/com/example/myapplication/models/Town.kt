package com.example.myapplication.models

data class Town(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val weather: Weather? = null
)
