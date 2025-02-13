package com.example.tbcexercises.data.local.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tbcexercises.data.local.room.daos.RemoteKeysDao
import com.example.tbcexercises.data.local.room.daos.UserDao
import com.example.tbcexercises.data.local.room.entity.RemoteKeyEntity
import com.example.tbcexercises.data.local.room.entity.UserEntity

@Database(
    entities = [UserEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UserDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}