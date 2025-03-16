package com.example.tbcexercises.feature_register.domain.use_case

import com.example.tbcexercises.core.domain.use_case.validation.ValidateEmailUseCase
import com.example.tbcexercises.core.domain.use_case.validation.ValidatePasswordUseCase

data class RegisterUseCaseWrapper(
    val registerUseCase: RegisterUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateRepeatPasswordUseCase: ValidateRepeatPasswordUseCase
)