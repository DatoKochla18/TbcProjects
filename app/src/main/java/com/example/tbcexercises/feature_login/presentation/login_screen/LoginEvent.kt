package com.example.tbcexercises.feature_login.presentation.login_screen

sealed interface LoginEvent {
    data class OnEmailChanged(val email:String):LoginEvent
    data class OnPasswordChanged(val password: String):LoginEvent
    data class Login(val email: String, val password: String) : LoginEvent
    data object SwitchCheckBoxStatus : LoginEvent
    data object SwitchShowPasswordStatus : LoginEvent
}