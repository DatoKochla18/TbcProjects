package com.example.tbcexercises.presentation.registerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.R
import com.example.tbcexercises.common.Resource
import com.example.tbcexercises.data.remote.request.AuthRequest
import com.example.tbcexercises.data.remote.response.LoginResponse
import com.example.tbcexercises.data.remote.response.RegisterResponse
import com.example.tbcexercises.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _registerResponse = MutableStateFlow<Resource<RegisterResponse>>(Resource.Loading)
    val registerResponse: StateFlow<Resource<RegisterResponse>> = _registerResponse


    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.register(email, password).collectLatest { state ->
                _registerResponse.value = state
            }
        }
    }
}