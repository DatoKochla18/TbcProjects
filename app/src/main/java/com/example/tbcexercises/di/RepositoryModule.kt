package com.example.tbcexercises.di

import com.example.tbcexercises.data.repository.LocationRepositoryImpl
import com.example.tbcexercises.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindItemRepository(impl: LocationRepositoryImpl): LocationRepository
}