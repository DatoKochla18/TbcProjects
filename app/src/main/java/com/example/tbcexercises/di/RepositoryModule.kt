package com.example.tbcexercises.di

import com.example.tbcexercises.data.repository.BreedRepositoryImpl
import com.example.tbcexercises.domain.repository.BreedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Singleton
    @Binds
    abstract fun binds(impl: BreedRepositoryImpl): BreedRepository
}