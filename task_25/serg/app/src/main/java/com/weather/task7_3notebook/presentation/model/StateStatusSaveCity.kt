package com.weather.task7_3notebook.presentation.model

sealed class StateStatusSaveCity {
    object Success : StateStatusSaveCity()
    data class Error(val message: String) : StateStatusSaveCity()
    object NoInternet : StateStatusSaveCity()
}
