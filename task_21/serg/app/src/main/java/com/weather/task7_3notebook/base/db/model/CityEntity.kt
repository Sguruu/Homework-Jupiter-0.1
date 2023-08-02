package com.weather.task7_3notebook.base.db.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weather.task7_3notebook.base.db.contract.CityContract

@Entity(tableName = CityContract.TABLE_NAME)
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(CityContract.Columns.ID)
    val id: Long = 0,
    @ColumnInfo(CityContract.Columns.CITY)
    val city: City,
    @Embedded
    @ColumnInfo(CityContract.Columns.WEATHER)
    val weather: Weather?
) {
    data class City(
        @ColumnInfo(CityContract.Columns.City.NAME_CITY)
        val nameCity: String,
        @ColumnInfo(CityContract.Columns.City.LATITUDE)
        val latitude: String,
        @ColumnInfo(CityContract.Columns.City.LONGITUDE)
        val longitude: String
    )

    data class Weather(
        @ColumnInfo(CityContract.Columns.Weather.TEMP_MIN)
        val tempMin: String,
        @ColumnInfo(CityContract.Columns.Weather.TEMP_MAX)
        val tempMax: String,
        @ColumnInfo(CityContract.Columns.Weather.DESCRIPTION_WEATHER)
        val descriptionWeather: String
    )
}
