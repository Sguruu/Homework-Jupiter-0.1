package com.weather.task7_3notebook.domain.interactor

import android.content.Context
import com.weather.task7_3notebook.data.mapper.toCity
import com.weather.task7_3notebook.data.mapper.toWeather
import com.weather.task7_3notebook.data.network.model.ResponseWeather
import com.weather.task7_3notebook.domain.common.Result
import com.weather.task7_3notebook.domain.mapper.toCityEntity
import com.weather.task7_3notebook.domain.model.City
import com.weather.task7_3notebook.domain.repository.BaseRepository
import com.weather.task7_3notebook.domain.repository.CityRepository
import com.weather.task7_3notebook.domain.repository.SearchRepository
import com.weather.task7_3notebook.domain.repository.WeatherRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

class CityInteractorImpl @Inject constructor(
    private val baseRepository: BaseRepository,
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository,
    private val searchRepository: SearchRepository
) : CityInteractor {

    override suspend fun addCity(city: City, context: Context): Result<City> {
        when (baseRepository.checkIsInternet(context)) {
            true -> {
                weatherRepository.requestWeather(city.latitude, city.longitude).apply {
                    return when (this) {
                        is Result.Success<ResponseWeather> -> {
                            val newCity = city.copy(weather = data.toWeather())
                            cityRepository.insertCity(newCity.toCityEntity())
                            Result.Success(newCity)
                        }

                        is Result.Error -> {
                            return this
                        }
                    }
                }
            }

            false -> {
                // если интернета нет, сохраняем город без погоды
                val newCity =
                    city.copy(
                        latitude = if (city.latitude.toDoubleOrNull() == null) "0.0"
                        else city.latitude,
                        longitude = if (city.longitude.toDoubleOrNull() == null) "0.0"
                        else city.longitude
                    )
                cityRepository.insertCity(newCity.toCityEntity())
                return Result.Error.ErrorNoInternet(data = newCity)
            }
        }
    }

    override suspend fun updateWeatherDataCities(
        context: Context,
        listCity: List<City>
    ): Result<List<City>>? {
        if (baseRepository.checkIsInternet(context)) {
            val newList = mutableListOf<City>()
            val jobsArray = arrayListOf<Job>()
            listCity.forEach { city ->
                // создаю массив jobs
                jobsArray.add(
                    coroutineScope {
                        launch {
                            weatherRepository.requestWeather(city.latitude, city.longitude).apply {
                                when (this) {
                                    is Result.Success<ResponseWeather> -> {
                                        newList.add(city.copy(weather = this.data.toWeather()))
                                    }

                                    is Result.Error -> {
                                        // заглушка
                                    }
                                }
                            }
                        }
                    }
                )
            }
            jobsArray.joinAll()
            return Result.Success(newList)
        } else {
            return null
        }
    }

    override fun getResultSearch(searchValue: String?, listCity: List<City>): List<City> =
        searchRepository.getResultSearchCity(searchValue, listCity)

    override suspend fun getAllCity(): Flow<List<City>> {
        return cityRepository.getAllCityDB()
            .apply {
                val items = this.firstOrNull()
                if (items.isNullOrEmpty()) {
                    createDefaultListCities()
                }
            }
            .map { list ->
                list.map { it.toCity() }
            }
    }

    override suspend fun updateCites(list: List<City>) {
        cityRepository.updateCites(list.map { it.toCityEntity() })
    }

    override suspend fun deleteCity(city: City) = cityRepository.deleteCity(city)

    /**
     * Добавить города по умолчанию в БД
     */
    private suspend fun createDefaultListCities() {
        cityRepository.insertCities(getDefaultListCities().map { it.toCityEntity() })
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
