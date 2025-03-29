package com.example.tbcexercises.di

import com.example.tbcexercises.data.repository.CardRepositoryImpl
import com.example.tbcexercises.domain.repository.CardRepository
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
    abstract fun bindItemRepository(impl: CardRepositoryImpl): CardRepository
}