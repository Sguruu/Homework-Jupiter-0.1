package com.weather.task7_3notebook.base.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.weather.task7_3notebook.base.db.contract.CityContract
import com.weather.task7_3notebook.base.db.model.CityEntity

@Dao
interface CityDao {
    /**
     * Получение всех городов
     */
    @Query("SELECT * FROM ${CityContract.TABLE_NAME}")
    suspend fun getAllCity(): List<CityEntity>

    /**
     * Метод добавления городов
     */
    @Insert
    suspend fun insertCity(cites: List<CityEntity>)

    /**
     * Получение города по id
     */
    @Query("SELECT * FROM ${CityContract.TABLE_NAME} WHERE ${CityContract.Columns.ID} = :id")
    suspend fun getCityById(id: Long)

    /**
     * Удаление города
     */
    @Delete
    suspend fun delete(cityEntity: CityEntity)

    /**
     * Удаление города по id
     */
    @Query("DELETE FROM ${CityContract.TABLE_NAME} WHERE ${CityContract.Columns.ID} = :cityID")
    suspend fun deleteCityById(cityID: Long)

    /**
     * Обновление города
     */
    @Update
    suspend fun updateCity(city: CityEntity)
}
