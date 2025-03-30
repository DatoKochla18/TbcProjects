package com.example.tbcexercises.di

import com.example.tbcexercises.data.repository.CardCheckRepositoryImpl
import com.example.tbcexercises.data.repository.CardRepositoryImpl
import com.example.tbcexercises.data.repository.CourseRepositoryImpl
import com.example.tbcexercises.domain.repository.CardCheckRepository
import com.example.tbcexercises.domain.repository.CardRepository
import com.example.tbcexercises.domain.repository.CourseRepository
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

    @Binds
    @Singleton
    abstract fun bindCardCheckRepository(impl: CardCheckRepositoryImpl): CardCheckRepository

    @Binds
    @Singleton
    abstract fun bindCourseRepository(impl: CourseRepositoryImpl): CourseRepository

}