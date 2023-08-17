package com.weather.task7_3notebook.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weather.task7_3notebook.data.db.contract.CityContract

@Entity(tableName = CityContract.TABLE_NAME)
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(CityContract.Columns.ID)
    val id: Long = 0,
    @Embedded
    val city: City,
    @Embedded
    val weather: Weather
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
        @ColumnInfo(name = CityContract.Columns.Weather.TEMP_MIN, defaultValue = "")
        val tempMin: String,
        @ColumnInfo(name = CityContract.Columns.Weather.TEMP_MAX, defaultValue = "")
        val tempMax: String,
        @ColumnInfo(name = CityContract.Columns.Weather.DESCRIPTION_WEATHER, defaultValue = "")
        val descriptionWeather: String
    )
}
