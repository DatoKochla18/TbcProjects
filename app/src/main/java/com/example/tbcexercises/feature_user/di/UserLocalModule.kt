package com.example.tbcexercises.feature_user.di

import com.example.tbcexercises.core.data.local.AppDatabase
import com.example.tbcexercises.feature_user.data.local.daos.RemoteKeysDao
import com.example.tbcexercises.feature_user.data.local.daos.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserLocalModule {

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.usersDao()
    }

    @Provides
    @Singleton
    fun provideRemoteKeysDao(database: AppDatabase): RemoteKeysDao {
        return database.remoteKeysDao()
    }

}