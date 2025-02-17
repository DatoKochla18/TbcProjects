package com.example.tbcexercises.presentation.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.repository.UserSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userSessionRepository: UserSessionRepository) :
    ViewModel() {

    val emailFlow = userSessionRepository.getEmailFlow()

    fun clearUserSession() {
        viewModelScope.launch {
            userSessionRepository.setSession(email = "", rememberMe = false)
        }
    }
}