package com.example.tbcexercises.di

import com.example.tbcexercises.domain.repository.LoginRepository
import com.example.tbcexercises.domain.repository.RegisterRepository
import com.example.tbcexercises.domain.use_case.LoginUseCase
import com.example.tbcexercises.domain.use_case.RegisterUseCase
import com.example.tbcexercises.domain.use_case.validation.ValidateEmailUseCase
import com.example.tbcexercises.domain.use_case.validation.ValidatePasswordUseCase
import com.example.tbcexercises.domain.use_case.validation.ValidateRepeatPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUserCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }

    @Provides
    fun provideRegisterUserCase(registerRepository: RegisterRepository): RegisterUseCase {
        return RegisterUseCase(registerRepository)
    }

    @Provides
    fun provideValidateEmailUseCase():ValidateEmailUseCase{
        return ValidateEmailUseCase()
    }
    @Provides
    fun provideValidatePasswordUseCase():ValidatePasswordUseCase{
        return ValidatePasswordUseCase()
    }
    @Provides
    fun provideValidateRepeatedPasswordUseCase():ValidateRepeatPasswordUseCase{
        return ValidateRepeatPasswordUseCase()
    }


}