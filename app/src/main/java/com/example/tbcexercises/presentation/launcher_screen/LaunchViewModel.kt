package com.example.tbcexercises.presentation.launcher_screen

import androidx.lifecycle.ViewModel
import com.example.tbcexercises.domain.manager.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(userSessionRepository: UserSessionManager) :
    ViewModel() {

    val rememberMe = userSessionRepository.getRememberMeFlow()
}