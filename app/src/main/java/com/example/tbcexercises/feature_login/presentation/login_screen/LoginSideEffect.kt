package com.example.tbcexercises.feature_login.presentation.login_screen

import com.example.tbcexercises.core.domain.util.error.NetworkError

sealed interface LoginSideEffect {
    object SuccessFullLogin : LoginSideEffect
    data class ShowToast(val message: NetworkError) : LoginSideEffect
}