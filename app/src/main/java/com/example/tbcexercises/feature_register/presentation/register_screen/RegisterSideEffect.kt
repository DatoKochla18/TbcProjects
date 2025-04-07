package com.example.tbcexercises.feature_register.presentation.register_screen

sealed interface RegisterSideEffect {
    data class NavigateToLoginScreen(val email: String, val password: String) : RegisterSideEffect
    data class ShowError(val message: Int) : RegisterSideEffect
}