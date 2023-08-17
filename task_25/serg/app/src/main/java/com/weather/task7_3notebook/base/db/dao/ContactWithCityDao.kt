package com.weather.task7_3notebook.base.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.weather.task7_3notebook.base.db.contract.ContactContract
import com.weather.task7_3notebook.base.db.model.ContactWithCityEntity

@Dao
interface ContactWithCityDao {
    /**
     * Получение всех контактов и связанными с ними городами
     */
    @Query("SELECT * FROM ${ContactContract.TABLE_NAME}")
    suspend fun getAllContactWithCityDao(): List<ContactWithCityEntity>
}
