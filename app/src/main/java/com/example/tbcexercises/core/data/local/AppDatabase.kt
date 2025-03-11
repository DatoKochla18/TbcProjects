package com.example.tbcexercises.core.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tbcexercises.feature_user.data.local.daos.RemoteKeysDao
import com.example.tbcexercises.feature_user.data.local.daos.UserDao
import com.example.tbcexercises.feature_user.data.local.entity.RemoteKeyEntity
import com.example.tbcexercises.feature_user.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UserDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}