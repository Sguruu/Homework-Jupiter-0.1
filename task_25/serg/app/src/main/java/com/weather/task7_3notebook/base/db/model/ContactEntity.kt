package com.weather.task7_3notebook.base.db.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weather.task7_3notebook.base.db.contract.ContactContract

// Указание названия таблицы
@Entity(tableName = ContactContract.TABLE_NAME)
data class ContactEntity(
    // указание первичного ключа через аннотацию @PrimaryKey
    @PrimaryKey(autoGenerate = true)
    // указать имя в колонке
    @ColumnInfo(ContactContract.Columns.ID)
    val id: Long = 0,
    @Embedded
    val contact: Contact
) {
    data class Contact(
        @ColumnInfo(ContactContract.Columns.Contact.NAME)
        val name: String,
        @ColumnInfo(ContactContract.Columns.Contact.LAST_NAME)
        val lastName: String,
        @ColumnInfo(ContactContract.Columns.Contact.NUMBER)
        val number: String
    )
}
