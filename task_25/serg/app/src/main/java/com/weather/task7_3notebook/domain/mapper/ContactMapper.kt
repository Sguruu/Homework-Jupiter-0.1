package com.weather.task7_3notebook.domain.mapper

import com.weather.task7_3notebook.data.db.model.ContactEntity
import com.weather.task7_3notebook.domain.model.Contact

fun Contact.toContactEntity(): ContactEntity =
    ContactEntity(
        contact = ContactEntity.Contact(
            name = this.name,
            lastName = this.lastName,
            number = this.number.toString()
        )
    )
