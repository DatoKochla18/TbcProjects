package com.example.tbcexercises.presentation.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.R
import com.example.tbcexercises.utils.common.Resource
import com.example.tbcexercises.data.remote.response.LoginResponse
import com.example.tbcexercises.domain.repository.AuthRepository
import com.example.tbcexercises.utils.exntension.isEmailValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginResponse = MutableStateFlow<Resource<LoginResponse>?>(null)
    val loginResponse: StateFlow<Resource<LoginResponse>?> = _loginResponse


    fun login(email: String, password: String) {
        _loginResponse.value = Resource.Loading
        if (!email.isEmailValid()) {
            _loginResponse.value = Resource.Error("Enter a valid email")
            return
        }
        if (password.isEmpty()) {
            _loginResponse.value = Resource.Error("Password should not be empty")
            return
        }
        if (password.length < 6) {
            _loginResponse.value = Resource.Error("Password should be at least 6 chars")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(email, password).collectLatest { state ->
                _loginResponse.value = state
            }
        }
    }

}