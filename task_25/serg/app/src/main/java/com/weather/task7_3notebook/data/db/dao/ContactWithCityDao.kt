package com.weather.task7_3notebook.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.weather.task7_3notebook.data.db.contract.ContactContract
import com.weather.task7_3notebook.data.db.model.ContactWithCityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactWithCityDao {
    /**
     * Получение всех контактов и связанными с ними городами
     */
    @Query("SELECT * FROM ${ContactContract.TABLE_NAME}")
    fun getAllContactWithCityDao(): Flow<List<ContactWithCityEntity>>
}
