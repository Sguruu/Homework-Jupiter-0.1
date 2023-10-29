package com.example.myapplication.models

data class ResponseRequestText(
    var main: Main?
)

data class Main(
    val temp: Double,
    val description: String,
    val humidity: Int
)
