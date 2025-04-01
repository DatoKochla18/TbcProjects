package com.example.tbcexercises.feature_register.presentation.register_screen

import com.example.tbcexercises.core.domain.util.error.NetworkError

sealed interface RegisterSideEffect {
    data class NavigateToLoginScreen(val email: String, val password: String) : RegisterSideEffect
    data class ShowError(val message: NetworkError) : RegisterSideEffect
}