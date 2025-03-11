package com.example.tbcexercises.feature_login.presentation.login_screen


data class LoginUiState(
    val isLoading: Boolean = false,
    val emailError: String? = "",
    val passwordError: String? = "",
    val isValidForm: Boolean = false
)
