package com.weather.task7_3notebook.data.db.contract

object ContactContract {
    const val TABLE_NAME = "ContactEntity"

    object Columns {
        const val ID = "id"

        object Contact {
            const val NAME = "name"
            const val LAST_NAME = "lastName"
            const val NUMBER = "number"
        }
    }
}
