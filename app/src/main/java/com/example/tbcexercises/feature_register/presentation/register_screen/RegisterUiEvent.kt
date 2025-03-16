package com.example.tbcexercises.feature_register.presentation.register_screen

import com.example.tbcexercises.core.domain.util.error.NetworkError

sealed interface RegisterUiEvent {
    object NavigateToLoginScreen : RegisterUiEvent
    data class ShowToast(val message: NetworkError) : RegisterUiEvent
}