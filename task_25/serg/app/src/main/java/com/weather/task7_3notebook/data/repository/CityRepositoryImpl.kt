package com.weather.task7_3notebook.data.repository

import com.weather.task7_3notebook.data.db.dao.CityDao
import com.weather.task7_3notebook.data.db.model.CityEntity
import com.weather.task7_3notebook.domain.model.City
import com.weather.task7_3notebook.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val db: CityDao
) : CityRepository {

    override suspend fun insertCity(city: CityEntity) {
        db.insertCity(city)
    }

    override suspend fun updateCites(list: List<CityEntity>) {
        list.forEach {
            db.updateCities(
                newTempMin = it.weather.tempMin,
                newTempMax = it.weather.tempMax,
                newDescriptionWeather = it.weather.descriptionWeather,
                nameCity = it.city.nameCity,
                latitude = it.city.latitude,
                longitude = it.city.longitude
            )
        }
    }

    override suspend fun deleteCity(city: City) {
        db.deleteCity(
            nameCity = city.nameCity,
            latitude = city.latitude,
            longitude = city.longitude
        )
    }

    override suspend fun insertCities(list: List<CityEntity>) {
        db.insertCities(list)
    }

    override fun getAllCityDB(): Flow<List<CityEntity>> {
        return db.getAllCity()
    }
}
