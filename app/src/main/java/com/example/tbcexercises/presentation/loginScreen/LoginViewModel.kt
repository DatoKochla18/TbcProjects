package com.example.tbcexercises.presentation.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.data.model.request.AuthRequest
import com.example.tbcexercises.data.model.response.LoginResponse
import com.example.tbcexercises.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginResponse = MutableStateFlow<Result<LoginResponse>?>(null)
    val loginResponse: StateFlow<Result<LoginResponse>?> = _loginResponse


    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginResponse.value = Result.Loading
            try {
                val response = LoginService.login(
                    authRequest = AuthRequest(
                        email = email,
                        password = password
                    )
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        _loginResponse.value = Result.Success(it)
                    } ?: unexpectedError()
                } else {
                    _loginResponse.value = Result.Error("Invalid Credentials")
                }
            } catch (e: Exception) {
                _loginResponse.value = Result.Error("Server Error plz try again")
            }
        }
    }

    private fun unexpectedError() {
        _loginResponse.value = Result.Error("Unexpected Error plz try again later")
    }
}