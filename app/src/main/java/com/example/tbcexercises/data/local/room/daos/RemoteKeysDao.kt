package com.example.tbcexercises.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tbcexercises.data.local.room.entity.RemoteKeyEntity

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM remote_keys WHERE userId = :userId")
    suspend fun remoteKeyByUserId(userId: Int): RemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeyEntity>)

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}