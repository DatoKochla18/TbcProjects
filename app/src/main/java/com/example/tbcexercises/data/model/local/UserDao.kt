package com.example.tbcexercises.data.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("select * from user")
    fun getUsers(): Flow<List<User>>

//    @Upsert
//    suspend fun upsert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<User>)

}
