package com.weather.task7_3notebook.repository

import com.weather.task7_3notebook.R
import com.weather.task7_3notebook.base.App
import com.weather.task7_3notebook.base.Result
import com.weather.task7_3notebook.base.network.Network
import com.weather.task7_3notebook.model.ResponseWeather
import com.weather.task7_3notebook.model.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {
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
                        }
                            ?: resultCallback
                                .invoke(
                                    Result.Error(
                                        RuntimeException(App.res.getString(R.string.parsing_error))
                                    )
                                )
                    } else {
                        resultCallback
                            .invoke(
                                Result.Error(
                                    RuntimeException(
                                        App.res.getString(R.string.incorrect_code_status)
                                    )
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
}
