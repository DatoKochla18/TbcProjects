package com.example.tbcexercises.feature_register.presentation.register_screen

sealed interface RegisterUiEvent {
    object NavigateToLoginScreen : RegisterUiEvent
    data class ShowToast(val message: String) : RegisterUiEvent
}