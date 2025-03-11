package com.example.tbcexercises.feature_login.presentation.login_screen

sealed interface LoginValidationEvent {
    data class LoginValidation(val email: String, val password: String) : LoginValidationEvent
    data class ValidateEmail(val email: String) : LoginValidationEvent
    data class ValidatePassword(val password: String) : LoginValidationEvent
}