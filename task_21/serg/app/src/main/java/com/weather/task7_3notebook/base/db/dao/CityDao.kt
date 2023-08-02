package com.weather.task7_3notebook.base.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.weather.task7_3notebook.base.db.contract.CityContract
import com.weather.task7_3notebook.base.db.model.CityEntity
import com.weather.task7_3notebook.model.City

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
    suspend fun getCityById(id: Long): CityEntity

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
     * Удаление города по : широте и долготе и имени города
     */
    @Query(
        "DELETE FROM ${CityContract.TABLE_NAME} WHERE " +
            "${CityContract.Columns.City.NAME_CITY} = :nameCity AND " +
            "${CityContract.Columns.City.LATITUDE} = :latitude AND " +
            "${CityContract.Columns.City.LONGITUDE} = :longitude"
    )
    suspend fun deleteCity(nameCity: String, latitude: String, longitude: String)

    /**
     * Обновление города
     */
    @Update
    suspend fun updateCity(city: CityEntity)
}
