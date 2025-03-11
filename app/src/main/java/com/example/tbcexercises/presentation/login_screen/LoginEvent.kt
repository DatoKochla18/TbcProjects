package com.example.tbcexercises.presentation.login_screen

sealed interface LoginEvent {
    data class Login(val email: String, val password: String) : LoginEvent
    data class ValidateEmail(val email: String) : LoginEvent
    data class ValidatePassword(val password: String) : LoginEvent
}