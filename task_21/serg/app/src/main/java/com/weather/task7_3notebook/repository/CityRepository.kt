package com.weather.task7_3notebook.repository

import com.weather.task7_3notebook.base.db.DataBase
import com.weather.task7_3notebook.base.db.model.CityEntity
import com.weather.task7_3notebook.model.City
import com.weather.task7_3notebook.model.Weather

class CityRepository {
    private val db = DataBase.instance.CityDao()

    /**
     * Получить города, если их нету в БД, то сначала города добавятся в БД
     */
    suspend fun getAllCity(): List<City> {
        val listCity = getAllCityDB()
        return listCity.ifEmpty {
            createDefaultListCities()
            getDefaultListCities()
        }
    }

    suspend fun insertCity(list: List<City>) {
        db.insertCity(
            list.map {
                CityEntity(
                    city = CityEntity.City(
                        nameCity = it.nameCity,
                        latitude = it.latitude,
                        longitude = it.longitude
                    ),
                    weather = if (it.weather == null) null else {
                        CityEntity.Weather(
                            tempMin = it.weather.tempMin.toString(),
                            tempMax = it.weather.tempMax.toString(),
                            descriptionWeather = it.weather.descriptionWeather
                        )
                    }
                )
            }
        )
    }

    suspend fun deleteCity(city: City) {
        db.deleteCity(
            nameCity = city.nameCity,
            latitude = city.latitude,
            longitude = city.longitude
        )
    }

    private suspend fun getAllCityDB(): List<City> {
        return db.getAllCity().map {
            City(
                nameCity = it.city.nameCity,
                latitude = it.city.latitude,
                longitude = it.city.longitude,
                weather = if (it.weather == null) null else Weather(
                    tempMin = it.weather.tempMin.toDoubleOrNull() ?: 0.0,
                    tempMax = it.weather.tempMax.toDoubleOrNull() ?: 0.0,
                    descriptionWeather = it.weather.descriptionWeather
                )
            )
        }
    }

    /**
     * Добавить города по умолчанию в БД
     */
    private suspend fun createDefaultListCities() {
        insertCity(getDefaultListCities())
    }

    /**
     * Создать список городов по умолчанию
     */
    private fun getDefaultListCities(): List<City> {
        return listOf(
            City(
                nameCity = "Самара",
                latitude = "53.18606181323793",
                longitude = "50.1213652999747"
            ),
            City(
                nameCity = "Москва",
                latitude = "55.7554138611996",
                longitude = "37.617702687368656"
            ),
            City(
                nameCity = "Сахалин",
                latitude = "46.961291841072416",
                longitude = "142.72710148603994"
            )
        )
    }
}
