package com.example.tbcexercises.di

import com.example.tbcexercises.data.repository.ImageRepositoryImpl
import com.example.tbcexercises.domain.repository.ImageRepository
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
    abstract fun bindImageRepository(impl:ImageRepositoryImpl):ImageRepository
}