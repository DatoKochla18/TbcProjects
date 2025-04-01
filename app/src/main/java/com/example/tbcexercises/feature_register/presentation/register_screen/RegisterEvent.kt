package com.example.tbcexercises.feature_register.presentation.register_screen

sealed interface RegisterEvent {
    data class Register(val email: String, val password: String) :
        RegisterEvent

    data class OnEmailChanged(val email: String) : RegisterEvent
    data class OnPasswordChanged(val password: String) : RegisterEvent
    data class OnRepeatedPasswordChanged(val repeatedPassword: String) :
        RegisterEvent

    data object OnShowPasswordChanged : RegisterEvent

}