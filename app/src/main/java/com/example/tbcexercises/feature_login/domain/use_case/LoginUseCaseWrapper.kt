package com.example.tbcexercises.feature_login.domain.use_case

import com.example.tbcexercises.core.domain.use_case.SaveValueToLocalStorageUseCase
import com.example.tbcexercises.core.domain.use_case.validation.ValidateEmailUseCase
import com.example.tbcexercises.core.domain.use_case.validation.ValidatePasswordUseCase

data class LoginUseCaseWrapper(
    val validateEmailUseCase: ValidateEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val loginUseCase: LoginUseCase,
    val saveValueToLocalStorageUseCase: SaveValueToLocalStorageUseCase
)
