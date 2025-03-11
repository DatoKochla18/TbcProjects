package com.example.tbcexercises.feature_register.presentation.register_screen

import com.example.tbcexercises.core.domain.util.ErrorTypes

data class RegisterUiState(
    val emailError: ErrorTypes? = ErrorTypes.NON_VALIDATED,
    val passwordError: ErrorTypes? = ErrorTypes.NON_VALIDATED,
    val repeatedPasswordError: ErrorTypes? = ErrorTypes.NON_VALIDATED,
    val isLoading: Boolean = false,
    val isValidForm: Boolean = false,
)