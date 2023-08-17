package com.weather.task7_3notebook.domain.repository

import com.weather.task7_3notebook.domain.model.City
import com.weather.task7_3notebook.domain.model.Contact

interface SearchRepository {
    /**
     * Получить результат поиска
     * @param searchValue значение для поиска
     * @param listContact список для поиска
     * @return результат поиска
     */
    fun getResultSearchContact(searchValue: String?, listContact: List<Contact>): List<Contact>

    /**
     * Получить результат поиска
     * @param searchValue значение для поиска
     * @param listCity список для поиска
     * @return результат поиска
     */
    fun getResultSearchCity(searchValue: String?, listCity: List<City>): List<City>
}
