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

    suspend fun insertCity(city: City) {
        val cityEntity = CityEntity(
            city = CityEntity.City(
                nameCity = city.nameCity,
                latitude = city.latitude,
                longitude = city.longitude
            ),
            weather =
            CityEntity.Weather(
                tempMin = city.weather?.tempMin.toString(),
                tempMax = city.weather?.tempMax.toString(),
                descriptionWeather = city.weather?.descriptionWeather ?: ""
            )
        )
        db.insertCity(cityEntity)
    }

    suspend fun updateCites(list: List<City>) {
        list.forEach {
            val cityEntity = CityEntity(
                city = CityEntity.City(
                    nameCity = it.nameCity,
                    latitude = it.latitude,
                    longitude = it.longitude
                ),
                weather = CityEntity.Weather(
                    tempMax = it.weather?.tempMax.toString(),
                    tempMin = it.weather?.tempMin.toString(),
                    descriptionWeather = it.weather?.descriptionWeather ?: ""
                )
            )
            db.updateCities(
                newTempMin = cityEntity.weather.tempMin,
                newTempMax = cityEntity.weather.tempMax,
                newDescriptionWeather = cityEntity.weather.descriptionWeather,
                nameCity = cityEntity.city.nameCity,
                latitude = cityEntity.city.latitude,
                longitude = cityEntity.city.longitude
            )
        }
    }

    suspend fun deleteCity(city: City) {
        db.deleteCity(
            nameCity = city.nameCity,
            latitude = city.latitude,
            longitude = city.longitude
        )
    }

    private suspend fun insertCities(list: List<City>) {
        db.insertCities(
            list.map {
                CityEntity(
                    city = CityEntity.City(
                        nameCity = it.nameCity,
                        latitude = it.latitude,
                        longitude = it.longitude
                    ),
                    weather =
                    CityEntity.Weather(
                        tempMin = it.weather?.tempMin.toString(),
                        tempMax = it.weather?.tempMax.toString(),
                        descriptionWeather = it.weather?.descriptionWeather ?: ""
                    )
                )
            }
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
        insertCities(getDefaultListCities())
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
