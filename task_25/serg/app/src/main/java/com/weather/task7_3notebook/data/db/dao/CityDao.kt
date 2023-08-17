package com.weather.task7_3notebook.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.weather.task7_3notebook.data.db.contract.CityContract
import com.weather.task7_3notebook.data.db.model.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    /**
     * Получение всех городов
     */
    @Query("SELECT * FROM ${CityContract.TABLE_NAME}")
    fun getAllCity(): Flow<List<CityEntity>>

    /**
     * Метод добавления городов
     */
    @Insert
    suspend fun insertCities(cites: List<CityEntity>)

    /**
     * Метод добавления города
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CityEntity)

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

    /**
     * Обновление города
     */
    @Query(
        "UPDATE ${CityContract.TABLE_NAME} SET " +
            "${CityContract.Columns.Weather.TEMP_MIN} = :newTempMin," +
            "${CityContract.Columns.Weather.TEMP_MAX} = :newTempMax," +
            "${CityContract.Columns.Weather.DESCRIPTION_WEATHER} = :newDescriptionWeather " +
            "WHERE ${CityContract.Columns.City.NAME_CITY} IN (:nameCity) AND " +
            "${CityContract.Columns.City.LATITUDE} IN (:latitude) AND " +
            "${CityContract.Columns.City.LONGITUDE} IN (:longitude) "
    )
    suspend fun updateCities(
        newTempMin: String,
        newTempMax: String,
        newDescriptionWeather: String,
        nameCity: String,
        latitude: String,
        longitude: String
    )
}
