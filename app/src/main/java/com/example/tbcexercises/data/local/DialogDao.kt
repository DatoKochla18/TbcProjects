package com.example.tbcexercises.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tbcexercises.data.model.Dialog

@Dao
interface DialogDao {

    @Query("SELECT * FROM dialog")
    suspend fun getDialogs(): List<Dialog>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dialogs: List<Dialog>)
}