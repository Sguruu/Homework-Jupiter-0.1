package com.weather.task7_3notebook.base.network

import com.weather.task7_3notebook.base.retrofit.WeatherApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Network {
    const val API_KEY = "a552b39b529b0402a3b40d2affee9ef4"
    const val LANG = "ru"
    const val UNITS = "metric"

    private val client = OkHttpClient.Builder()
        // добавление network Interceptor
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    // создаем retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        // позволяет нам работать с конвертором от Moshi
        .addConverterFactory(MoshiConverterFactory.create())
        // указываем наш клиент, если килент не будем указывать он создасть пустой client без NetworkInterceptor
        .client(client)
        .build()

    val weatherApi: WeatherApi
        // создает  наш интерфейс путем создания экземпляра ретрофит
        get() = retrofit.create()
}
