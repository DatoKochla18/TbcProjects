package com.example.tbcexercises.presentation.register_screen

sealed interface RegisterEvent {
    data class Register(val email: String, val password: String, val repeatedPassword: String):RegisterEvent
    data class EmailChanged(val email: String) : RegisterEvent
    data class PasswordChanged(val password: String) : RegisterEvent
    data class RepeatedPasswordChanged(val password: String, val repeatedPassword: String) :
        RegisterEvent
}