package com.example.myapplication

enum class ErrorLogin (val text:String){
    EMPTYINPUT("Ошибка. Не введён логин, либо пароль"),
    INVALIDEMAIL("Ошибка. Введён некорректный адрес электронной почты")
}