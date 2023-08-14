package com.weather.task7_3notebook.base.db.contract

object CityContract {
    const val TABLE_NAME = "CityEntity"

    object Columns {
        const val ID = "id"

        object City {
            const val NAME_CITY = "nameCity"
            const val LATITUDE = "latitude"
            const val LONGITUDE = "longitude"
        }

        object Weather {
            const val TEMP_MIN = "tempMin"
            const val TEMP_MAX = "tempMax"
            const val DESCRIPTION_WEATHER = "descriptionWeather"
        }
    }
}
