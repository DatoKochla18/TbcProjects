package com.example.tbcexercises.presentation.launcherScreen

import androidx.lifecycle.ViewModel
import com.example.tbcexercises.domain.repository.UserSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(userSessionRepository: UserSessionRepository) :
    ViewModel() {

    val rememberMe = userSessionRepository.getRememberMeFlow()
}