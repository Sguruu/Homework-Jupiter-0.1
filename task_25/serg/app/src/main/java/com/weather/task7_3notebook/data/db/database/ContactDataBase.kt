package com.weather.task7_3notebook.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.task7_3notebook.data.db.dao.CityDao
import com.weather.task7_3notebook.data.db.dao.ContactDao
import com.weather.task7_3notebook.data.db.dao.ContactWithCityDao
import com.weather.task7_3notebook.data.db.database.ContactDataBase.Companion.DB_VERSION
import com.weather.task7_3notebook.data.db.model.CityEntity
import com.weather.task7_3notebook.data.db.model.ContactEntity

@Database(entities = [ContactEntity::class, CityEntity::class], version = DB_VERSION)
abstract class ContactDataBase : RoomDatabase() {
    abstract fun ContactDao(): ContactDao
    abstract fun CityDao(): CityDao
    abstract fun ContactWithCityDao(): ContactWithCityDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "APP_ROOM_DB"
    }
}
