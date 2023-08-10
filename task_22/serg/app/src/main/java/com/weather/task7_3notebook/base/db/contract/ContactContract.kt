package com.weather.task7_3notebook.base.db.contract

object ContactContract {
    const val TABLE_NAME = "ContactEntity"

    object Columns {
        const val ID = "id"
        const val CONTACT = "contact"

        object Contact {
            const val NAME = "name"
            const val LAST_NAME = "lastName"
            const val NUMBER = "number"
        }
    }
}
