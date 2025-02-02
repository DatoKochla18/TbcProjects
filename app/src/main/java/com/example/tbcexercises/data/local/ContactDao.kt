package com.example.tbcexercises.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tbcexercises.data.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getContacts(): Flow<List<Contact>>


    @Upsert
    suspend fun upsert(contact: Contact)

    @Query("SELECT * FROM contact where id = :id")
    suspend fun getContactById(id: Int): Contact

    @Query("SELECT * FROM contact where phoneNumber like '%' || :phoneNumber || '%'  ORDER BY name ASC")
    fun getContactsByPhoneNumber(phoneNumber: String): Flow<List<Contact>>
}