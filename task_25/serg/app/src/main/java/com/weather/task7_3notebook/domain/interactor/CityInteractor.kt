package com.weather.task7_3notebook.domain.interactor

import android.content.Context
import com.weather.task7_3notebook.domain.common.Result
import com.weather.task7_3notebook.domain.model.City
import kotlinx.coroutines.flow.Flow

interface CityInteractor {
    /**
     * Функция добавления города
     */
    suspend fun addCity(city: City, context: Context): Result<City>

    /**
     * @return null - если нет интернета
     */
    suspend fun updateWeatherDataCities(context: Context, listCity: List<City>): Result<List<City>>?

    fun getResultSearch(searchValue: String?, listCity: List<City>): List<City>

    /**
     * Получить города, если их нету в БД, то сначала города добавятся в БД
     */
    suspend fun getAllCity(): Flow<List<City>>
    suspend fun updateCites(list: List<City>)
    suspend fun deleteCity(city: City)
}
