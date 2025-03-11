package com.example.tbcexercises.presentation.login_screen

sealed interface LoginUiEvent {
    object SuccessFullLogin : LoginUiEvent
    data class ShowToast(val message: String) : LoginUiEvent
}