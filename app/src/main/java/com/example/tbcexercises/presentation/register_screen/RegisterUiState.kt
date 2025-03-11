package com.example.tbcexercises.presentation.register_screen

data class RegisterUiState(
    val emailError: String? = "",
    val passwordError: String? = "",
    val repeatedPasswordError: String? = "",
    val isLoading: Boolean = false,
    val isValidForm: Boolean = false
)