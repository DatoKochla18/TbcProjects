package com.example.tbcexercises.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tbcexercises.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<UserEntity>)

    @Query("SELECT * FROM users")
    fun getUsers(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM users")
    suspend fun clearUsers()
}