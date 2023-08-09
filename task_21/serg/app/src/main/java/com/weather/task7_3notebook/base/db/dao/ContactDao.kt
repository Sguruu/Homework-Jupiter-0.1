package com.weather.task7_3notebook.base.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.weather.task7_3notebook.base.db.contract.ContactContract
import com.weather.task7_3notebook.base.db.model.ContactEntity

@Dao
interface ContactDao {
    /**
     * Получение всех контактов
     */
    @Query("SELECT * FROM ${ContactContract.TABLE_NAME}")
    suspend fun getAllContact(): List<ContactEntity>

    /**
     * Метод добавления контакта
     */
    @Insert
    suspend fun insertContact(contact: List<ContactEntity>)

    /**
     * Получение контакта по id
     */
    @Query("SELECT * FROM ${ContactContract.TABLE_NAME} WHERE ${ContactContract.Columns.ID} = :id")
    suspend fun getContactById(id: Long): ContactEntity

    /**
     * Удаление контакта
     */
    @Delete
    suspend fun delete(contactEntity: ContactEntity)

    /**
     * Удаление контакта по id
     */
    @Query(
        "DELETE FROM ${ContactContract.TABLE_NAME} WHERE ${ContactContract.Columns.ID} = " +
            ":contactID"
    )
    suspend fun deleteContactById(contactID: Long)

    /**
     * Обновление контакта
     */
    @Update
    suspend fun updateCity(contact: ContactEntity)
}
