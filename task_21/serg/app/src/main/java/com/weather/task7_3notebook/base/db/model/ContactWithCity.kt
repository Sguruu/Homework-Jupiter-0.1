package com.weather.task7_3notebook.base.db.model

import androidx.room.Embedded
import androidx.room.Relation
import com.weather.task7_3notebook.base.db.contract.CityContract
import com.weather.task7_3notebook.base.db.contract.ContactContract

// Установка связи 1 + 1
// https://medium.com/androiddevelopers/database-relations-with-room-544ab95e4542
data class ContactWithCityEntity(
    @Embedded
    val contact: ContactEntity,
    @Relation(parentColumn = ContactContract.Columns.ID, entityColumn = CityContract.Columns.ID)
    val city: CityEntity
)
