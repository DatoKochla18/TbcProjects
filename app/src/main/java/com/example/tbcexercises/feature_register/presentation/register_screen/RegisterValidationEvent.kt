package com.example.tbcexercises.feature_register.presentation.register_screen

sealed interface RegisterValidationEvent {
    data class Register(val email: String, val password: String, val repeatedPassword: String):
        RegisterValidationEvent
    data class EmailChanged(val email: String) : RegisterValidationEvent
    data class PasswordChanged(val password: String) : RegisterValidationEvent
    data class RepeatedPasswordChanged(val password: String, val repeatedPassword: String) :
        RegisterValidationEvent
}