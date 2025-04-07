package com.example.tbcexercises.feature_login.presentation.login_screen

sealed interface LoginSideEffect {
    object SuccessFullLogin : LoginSideEffect
    data class ShowSnackBar(val message: Int) : LoginSideEffect
}