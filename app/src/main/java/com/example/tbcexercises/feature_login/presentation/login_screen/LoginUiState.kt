package com.example.tbcexercises.feature_login.presentation.login_screen

import com.example.tbcexercises.core.domain.util.error.EmailError
import com.example.tbcexercises.core.domain.util.error.PasswordError


data class LoginUiState(
    val isLoading: Boolean = false,
    val emailError: EmailError? = null,
    val passwordError: PasswordError? = null,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isValidForm: Boolean = false,
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val showPassword: Boolean = false,
)
