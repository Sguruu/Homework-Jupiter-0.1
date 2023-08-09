package com.weather.task7_3notebook.repository

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.weather.task7_3notebook.base.Result
import com.weather.task7_3notebook.base.network.Network
import com.weather.task7_3notebook.model.ResponseErrorWeather
import com.weather.task7_3notebook.model.ResponseWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

class WeatherRepository {
    private val moshi by lazy {
        Moshi.Builder().build()
    }

    suspend fun requestWeather(
        lat: String,
        lon: String
    ): Result<ResponseWeather> {
        return withContext(Dispatchers.IO) {
            try {
                val responseWeather = Network.weatherApi.getWeather(lat, lon)
                Result.Success<ResponseWeather>(responseWeather)
            } catch (e: HttpException) {
                if (e.code() == 400) {
                    var errorMessage: String? = null
                    e.response()?.errorBody()?.string()?.let { valueJson ->
                        errorMessage = parseErrorResponse(valueJson)?.message
                    }
                    Result.Error(e, errorValue = errorMessage)
                } else {
                    Result.Error(e)
                }
            } catch (e: IOException) {
                // handle no internet connection
                Result.Error(e, "Нет интернета")
            }
        }
    }

    private fun parseErrorResponse(valueJson: String): ResponseErrorWeather? {
        return try {
            val jsonAdapter: JsonAdapter<ResponseErrorWeather> =
                moshi.adapter(ResponseErrorWeather::class.java)
            jsonAdapter.fromJson(valueJson)
        } catch (e: Throwable) {
            null
        }
    }
}
