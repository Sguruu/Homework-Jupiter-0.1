package com.weather.task7_3notebook.domain.model

import com.weather.task7_3notebook.domain.model.City

data class Contact(
    val name: String,
    val lastName: String,
    val number: Long,
    val city: City?
)
