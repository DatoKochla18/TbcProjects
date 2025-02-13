package com.example.tbcexercises.presentation.registerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.utils.common.Resource
import com.example.tbcexercises.data.remote.response.RegisterResponse
import com.example.tbcexercises.domain.repository.AuthRepository
import com.example.tbcexercises.utils.exntension.isEmailValid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _registerResponse = MutableStateFlow<Resource<RegisterResponse>?>(null)
    val registerResponse: StateFlow<Resource<RegisterResponse>?> = _registerResponse


    fun register(email: String, password: String, passwordRepeat: String) {
        _registerResponse.value = Resource.Loading
        if (!email.isEmailValid()) {
            _registerResponse.value = Resource.Error("Enter a Valid Email")
        } else if (password.isEmpty()) {
            _registerResponse.value = Resource.Error("Password should not be empty")
        } else if (passwordRepeat != password) {
            _registerResponse.value = Resource.Error("Passwords should match")
        } else if (password.length < 6) {
            _registerResponse.value = Resource.Error("Password should 6 chars")
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                authRepository.register(email, password).collectLatest { state ->
                    _registerResponse.value = state
                }
            }
        }
    }
}