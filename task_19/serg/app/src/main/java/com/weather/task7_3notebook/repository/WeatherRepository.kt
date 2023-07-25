package com.weather.task7_3notebook.repository

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.weather.task7_3notebook.R
import com.weather.task7_3notebook.base.App
import com.weather.task7_3notebook.base.Result
import com.weather.task7_3notebook.base.network.Network
import com.weather.task7_3notebook.model.ResponseErrorWeather
import com.weather.task7_3notebook.model.ResponseWeather
import com.weather.task7_3notebook.model.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {
    private val moshi by lazy {
        Moshi.Builder().build()
    }

    fun requestWeather(
        lat: String,
        lon: String,
        resultCallback: (Result<Weather>) -> Unit
    ): Call<ResponseWeather> {
        return Network.weatherApi.getWeather(lat, lon).apply {
            enqueue(object : Callback<ResponseWeather> {
                // если запрос успешен
                override fun onResponse(
                    call: Call<ResponseWeather>,
                    response: Response<ResponseWeather>
                ) {
                    if (response.isSuccessful) {
                        val weather = response.body()?.let {
                            Weather(
                                tempMin = it.main.tempMin,
                                tempMax = it.main.tempMax,
                                descriptionWeather = it.weatherCurrent[0].description
                            )
                        }

                        weather?.let {
                            resultCallback.invoke(Result.Success(it))
                        } ?: resultCallback.invoke(
                            Result.Error(
                                RuntimeException(App.res.getString(R.string.parsing_error))
                            )
                        )
                    } else {
                        var errorMessage: String? = null
                        response.errorBody()?.string()?.let { valueJson ->
                            errorMessage = parseErrorResponse(valueJson)?.message
                        }
                        resultCallback
                            .invoke(
                                Result.Error(
                                    RuntimeException(
                                        App.res.getString(R.string.incorrect_code_status)
                                    ),
                                    errorMessage
                                )
                            )
                    }
                }

                override fun onFailure(call: Call<ResponseWeather>, t: Throwable) {
                    resultCallback.invoke(Result.Error(t))
                }
            })
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
