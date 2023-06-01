package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorLoginCondition(val message: String): Parcelable {

    fun emptyInput():ErrorLoginCondition{
        return ErrorLoginCondition("Ошибка. Не введён логин, либо пароль")
    }
    fun invalidEmail():ErrorLoginCondition{
        return ErrorLoginCondition("Ошибка. Введён некорректный адрес электронной почты")
    }
    fun noError():ErrorLoginCondition{
        return ErrorLoginCondition("")
    }
}
