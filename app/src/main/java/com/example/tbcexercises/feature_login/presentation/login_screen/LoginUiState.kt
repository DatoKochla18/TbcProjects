package com.example.tbcexercises.feature_login.presentation.login_screen

import com.example.tbcexercises.core.domain.util.ErrorTypes


data class LoginUiState(
    val isLoading: Boolean = false,
    val emailError: ErrorTypes? = ErrorTypes.NON_VALIDATED,
    val passwordError: ErrorTypes? = ErrorTypes.NON_VALIDATED,
    val isValidForm: Boolean = false,
)
