package com.example.tbcexercises.presentation.login_screen

import com.example.tbcexercises.domain.model.User

data class LoginUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val emailError: String? = "",
    val passwordError: String? = "",
    val isValidForm: Boolean = false
)
