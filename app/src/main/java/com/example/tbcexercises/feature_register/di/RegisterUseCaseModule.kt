package com.example.tbcexercises.feature_register.di

import com.example.tbcexercises.core.domain.use_case.validation.ValidateEmailUseCase
import com.example.tbcexercises.core.domain.use_case.validation.ValidatePasswordUseCase
import com.example.tbcexercises.core.domain.use_case.validation.ValidateRepeatPasswordUseCase
import com.example.tbcexercises.feature_register.domain.repository.RegisterRepository
import com.example.tbcexercises.feature_register.domain.use_case.RegisterUseCase
import com.example.tbcexercises.feature_register.domain.use_case.RegisterUseWrapper
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
    fun provideRegisterUseCaseWrapper(
        registerUseCase: RegisterUseCase,
        validateEmailUseCase: ValidateEmailUseCase,
        validatePasswordUseCase: ValidatePasswordUseCase,
        validateRepeatPasswordUseCase: ValidateRepeatPasswordUseCase
    ): RegisterUseWrapper {
        return RegisterUseWrapper(
            registerUseCase = registerUseCase,
            validateEmailUseCase = validateEmailUseCase,
            validatePasswordUseCase = validatePasswordUseCase,
            validateRepeatPasswordUseCase = validateRepeatPasswordUseCase
        )
    }
}