package com.weather.task7_3notebook.data.repository

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.weather.task7_3notebook.App
import com.weather.task7_3notebook.R
import com.weather.task7_3notebook.data.network.model.ResponseErrorWeather
import com.weather.task7_3notebook.data.network.model.ResponseWeather
import com.weather.task7_3notebook.data.network.retrofit.WeatherApi
import com.weather.task7_3notebook.domain.common.Result
import com.weather.task7_3notebook.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {
    private val moshi by lazy {
        Moshi.Builder().build()
    }

    override suspend fun requestWeather(
        lat: String,
        lon: String
    ): Result<ResponseWeather> {
        return withContext(Dispatchers.IO) {
            try {
                val responseWeather = weatherApi.getWeather(lat, lon)
                Result.Success<ResponseWeather>(responseWeather)
            } catch (e: HttpException) {
                if (e.code() == 400) {
                    var errorMessage: String? = null
                    e.response()?.errorBody()?.string()?.let { valueJson ->
                        errorMessage = parseErrorResponse(valueJson)?.message
                    }
                    Result.Error.ErrorResponse(e, errorValue = errorMessage)
                } else {
                    Result.Error.ErrorResponse(e)
                }
            } catch (e: IOException) {
                // handle no internet connection
                Result.Error.ErrorNoInternet<Nothing>(
                    e,
                    errorValue = App.res.getString(R.string.error_no_internet)
                )
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
