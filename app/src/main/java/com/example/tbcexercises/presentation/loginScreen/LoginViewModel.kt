package com.example.tbcexercises.presentation.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.R
import com.example.tbcexercises.common.Resource
import com.example.tbcexercises.data.remote.request.AuthRequest
import com.example.tbcexercises.data.remote.response.LoginResponse
import com.example.tbcexercises.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginResponse = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading)
    val loginResponse: StateFlow<Resource<LoginResponse>> = _loginResponse


    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(email, password).collectLatest { state ->
                _loginResponse.value = state
            }
        }
    }


}