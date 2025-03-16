package com.example.tbcexercises.feature_user.presentation.di

import com.example.tbcexercises.feature_user.domain.repository.UserRepository
import com.example.tbcexercises.feature_user.domain.use_case.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserUseCaseModule {
    @Provides
    fun provideUserUseCase(userRepository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(userRepository)
    }
}