package com.example.tbcexercises.feature_register.presentation.register_screen

import com.example.tbcexercises.core.domain.util.error.NetworkError

sealed interface RegisterSideEffect {
    object NavigateToLoginScreen : RegisterSideEffect
    data class ShowToast(val message: NetworkError) : RegisterSideEffect
}