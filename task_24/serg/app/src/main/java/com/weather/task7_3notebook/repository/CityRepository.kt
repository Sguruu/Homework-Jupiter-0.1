package com.weather.task7_3notebook.repository

import com.weather.task7_3notebook.model.City

interface CityRepository {

    /**
     * Получить города, если их нету в БД, то сначала города добавятся в БД
     */
    suspend fun getAllCity(): List<City>
    suspend fun insertCity(city: City)
    suspend fun updateCites(list: List<City>)
    suspend fun deleteCity(city: City)
}