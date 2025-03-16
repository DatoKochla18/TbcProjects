package com.example.tbcexercises.feature_register.presentation.di

import com.example.tbcexercises.core.domain.use_case.validation.ValidateEmailUseCase
import com.example.tbcexercises.core.domain.use_case.validation.ValidatePasswordUseCase
import com.example.tbcexercises.feature_register.domain.repository.RegisterRepository
import com.example.tbcexercises.feature_register.domain.use_case.RegisterUseCase
import com.example.tbcexercises.feature_register.domain.use_case.RegisterUseCaseWrapper
import com.example.tbcexercises.feature_register.domain.use_case.ValidateRepeatPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RegisterUseCaseModule {

    @Provides
    fun provideRegisterUserCase(registerRepository: RegisterRepository): RegisterUseCase {
        return RegisterUseCase(registerRepository)
    }

    @Provides
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordUseCase()
    }

    @Provides
    fun provideRegisterUseCaseWrapper(
        registerUseCase: RegisterUseCase,
        validateEmailUseCase: ValidateEmailUseCase,
        validatePasswordUseCase: ValidatePasswordUseCase,
        validateRepeatPasswordUseCase: ValidateRepeatPasswordUseCase,
    ): RegisterUseCaseWrapper {
        return RegisterUseCaseWrapper(
            registerUseCase = registerUseCase,
            validateEmailUseCase = validateEmailUseCase,
            validatePasswordUseCase = validatePasswordUseCase,
            validateRepeatPasswordUseCase = validateRepeatPasswordUseCase
        )
    }
}