package com.weather.task7_3notebook.repository

import com.weather.task7_3notebook.model.City

class CityRepository {
    /**
     * Создать список городов по умолчанию
     */
    fun createDefaultListCities(): List<City> {
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
