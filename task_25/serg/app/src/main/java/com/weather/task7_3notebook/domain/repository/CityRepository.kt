package com.weather.task7_3notebook.domain.repository

import com.weather.task7_3notebook.data.db.model.CityEntity
import com.weather.task7_3notebook.domain.model.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    /**
     * Получить города, если их нету в БД, то сначала города добавятся в БД
     */
    fun getAllCityDB(): Flow<List<CityEntity>>
    suspend fun insertCity(city: CityEntity)
    suspend fun insertCities(list: List<CityEntity>)
    suspend fun updateCites(list: List<CityEntity>)
    suspend fun deleteCity(city: City)
}
