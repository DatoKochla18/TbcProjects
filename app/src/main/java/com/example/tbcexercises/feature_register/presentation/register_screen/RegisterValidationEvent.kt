package com.example.tbcexercises.feature_register.presentation.register_screen

sealed interface RegisterValidationEvent {
    data class Register(val email: String, val password: String):
        RegisterValidationEvent
    data class ValidateEmail(val email: String) : RegisterValidationEvent
    data class ValidatePassword(val password: String) : RegisterValidationEvent
    data class ValidateRepeatedPassword(val password: String, val repeatedPassword: String) :
        RegisterValidationEvent
}