package com.example.tbcexercises.di

import com.example.tbcexercises.data.manager.ConnectivityManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityManager(
        impl: ConnectivityManagerImpl
    ): com.example.tbcexercises.domain.manager.ConnectivityManager
}