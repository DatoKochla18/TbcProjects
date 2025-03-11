package com.example.tbcexercises.core.di

import com.example.tbcexercises.core.domain.manager.UserSessionManager
import com.example.tbcexercises.feature_register.domain.repository.RegisterRepository
import com.example.tbcexercises.core.domain.use_case.GetValueFromLocalStorageUseCase
import com.example.tbcexercises.feature_register.domain.use_case.RegisterUseCase
import com.example.tbcexercises.core.domain.use_case.SaveValueToLocalStorageUseCase
import com.example.tbcexercises.core.domain.use_case.validation.ValidateEmailUseCase
import com.example.tbcexercises.core.domain.use_case.validation.ValidatePasswordUseCase
import com.example.tbcexercises.core.domain.use_case.validation.ValidateRepeatPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Provides
    fun provideGetValueFromLocalStorageUserCase(userSessionManager: UserSessionManager): GetValueFromLocalStorageUseCase {
        return GetValueFromLocalStorageUseCase(userSessionManager)
    }

    @Provides
    fun provideSaveValueFromLocalStorageUserCase(userSessionManager: UserSessionManager): SaveValueToLocalStorageUseCase {
        return SaveValueToLocalStorageUseCase(userSessionManager)
    }

    @Provides
    fun provideValidateEmailUseCase(): ValidateEmailUseCase {
        return ValidateEmailUseCase()
    }

    @Provides
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordUseCase()
    }

    @Provides
    fun provideValidateRepeatedPasswordUseCase(): ValidateRepeatPasswordUseCase {
        return ValidateRepeatPasswordUseCase()
    }


}