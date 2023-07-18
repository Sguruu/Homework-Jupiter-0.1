package com.weather.task7_3notebook.base

sealed class Result<out T> {
    data class Success<out T : Any>(val data: T) : Result<T>()

    /**
     * Ничто не имеет экземпляров. Вы можете использовать Nothing для представления «значения,
     * которого никогда не существует»: например, если функция имеет возвращаемый тип Nothing,
     * это означает, что она никогда не возвращает значение (всегда выдает исключение).
     */
    data class Error(val error: Throwable) : Result<Nothing>()
}
