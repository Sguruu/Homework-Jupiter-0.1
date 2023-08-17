package com.weather.task7_3notebook.repository

import com.weather.task7_3notebook.model.Contact

interface SearchRepository {
    /**
     * Получить результат поиска
     * @param searchValue значение для поиска
     * @param listContact список для поиска
     * @return результат поиска
     */
    fun getResultSearch(searchValue: String?, listContact: List<Contact>): List<Contact>
}