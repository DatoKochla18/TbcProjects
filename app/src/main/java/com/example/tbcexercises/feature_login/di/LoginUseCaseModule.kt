package com.example.tbcexercises.feature_login.di

import com.example.tbcexercises.core.domain.use_case.SaveValueToLocalStorageUseCase
import com.example.tbcexercises.core.domain.use_case.validation.ValidateEmailUseCase
import com.example.tbcexercises.core.domain.use_case.validation.ValidatePasswordUseCase
import com.example.tbcexercises.feature_login.domain.use_case.LoginUseCase
import com.example.tbcexercises.feature_login.domain.use_case.LoginUseCaseWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LoginUseCaseModule {

    @Provides
    fun provideLoginUserCaseWrapper(
        loginUseCase: LoginUseCase,
        validateEmailUseCase: ValidateEmailUseCase,
        passwordUseCase: ValidatePasswordUseCase,
        saveValueToLocalStorageUseCase: SaveValueToLocalStorageUseCase

    ): LoginUseCaseWrapper {
        return LoginUseCaseWrapper(
            loginUseCase = loginUseCase,
            validateEmailUseCase = validateEmailUseCase,
            validatePasswordUseCase = passwordUseCase,
            saveValueToLocalStorageUseCase = saveValueToLocalStorageUseCase
        )
    }
}