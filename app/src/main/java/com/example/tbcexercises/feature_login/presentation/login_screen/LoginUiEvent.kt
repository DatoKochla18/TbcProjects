package com.example.tbcexercises.feature_login.presentation.login_screen

sealed interface LoginUiEvent {
    object SuccessFullLogin : LoginUiEvent
    data class ShowToast(val message: String) : LoginUiEvent
}