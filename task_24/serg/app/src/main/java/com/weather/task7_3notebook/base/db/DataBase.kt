package com.weather.task7_3notebook.base.db

import android.content.Context
import androidx.room.Room
import com.weather.task7_3notebook.base.db.database.ContactDataBase

object DataBase {
    lateinit var instance: ContactDataBase
        private set

    /**
     * Функция инициализатор DataBase
     * @param context контекст приложения
     */
    fun init(context: Context) {
        // будет сохранять на диск
        instance = Room.databaseBuilder(
            context,
            ContactDataBase::class.java,
            ContactDataBase.DB_NAME
        ).build()
    }
}
