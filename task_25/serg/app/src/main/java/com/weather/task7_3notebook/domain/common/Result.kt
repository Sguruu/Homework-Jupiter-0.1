package com.weather.task7_3notebook.domain.common

sealed class Result<out T> {
    data class Success<out T : Any>(val data: T) : Result<T>()

    /**
     * Ничто не имеет экземпляров. Вы можете использовать Nothing для представления «значения,
     * которого никогда не существует»: например, если функция имеет возвращаемый тип Nothing,
     * это означает, что она никогда не возвращает значение (всегда выдает исключение).
     */
    sealed class Error : Result<Nothing>() {
        data class ErrorResponse(
            val error: Throwable,
            val errorValue: String? = null
        ) : Error()

        data class ErrorNoInternet<out T : Any>(
            val error: Throwable? = null,
            val errorValue: String? = null,
            val data: T? = null
        ) : Error()
    }
}
