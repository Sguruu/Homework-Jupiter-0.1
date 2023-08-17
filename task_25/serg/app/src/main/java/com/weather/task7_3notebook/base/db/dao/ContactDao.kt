package com.weather.task7_3notebook.base.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.weather.task7_3notebook.base.db.contract.ContactContract
import com.weather.task7_3notebook.base.db.model.ContactEntity

@Dao
interface ContactDao {

    /**
     * Метод добавления контакта
     */
    @Insert
    suspend fun insertContact(contact: ContactEntity)

    /**
     * Получение контакта по id
     */
    @Query("SELECT * FROM ${ContactContract.TABLE_NAME} WHERE ${ContactContract.Columns.ID} = :id")
    suspend fun getContactById(id: Long): ContactEntity

    /**
     * Удаление контакта по : имени и фамилии и номера телефона
     */
    @Query(
        "DELETE FROM ${ContactContract.TABLE_NAME} WHERE " +
            "${ContactContract.Columns.Contact.NAME} = :name AND " +
            "${ContactContract.Columns.Contact.LAST_NAME} = :lastName AND " +
            "${ContactContract.Columns.Contact.NUMBER} = :number"
    )
    suspend fun deleteContact(name: String, lastName: String, number: String)

    /**
     * Обновление контакта
     */
    @Update
    suspend fun updateCity(contact: ContactEntity)
}
