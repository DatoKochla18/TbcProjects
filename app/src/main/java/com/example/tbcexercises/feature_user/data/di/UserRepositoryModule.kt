package com.example.tbcexercises.feature_user.data.di

import com.example.tbcexercises.feature_user.data.repository.UserRepositoryImpl
import com.example.tbcexercises.feature_user.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository


}