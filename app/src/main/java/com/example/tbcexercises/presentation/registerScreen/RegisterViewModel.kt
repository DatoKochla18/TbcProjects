package com.example.tbcexercises.presentation.registerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.data.model.request.AuthRequest
import com.example.tbcexercises.data.model.response.RegisterResponse
import com.example.tbcexercises.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _registerResponse = MutableStateFlow<Result<RegisterResponse>?>(null)
    val registerResponse: StateFlow<Result<RegisterResponse>?> = _registerResponse


    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _registerResponse.value = Result.Loading
            try {
                val response = RegisterService.register(
                    authRequest = AuthRequest(
                        email = email,
                        password = password
                    )
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        _registerResponse.value = Result.Success(it)
                    } ?: unexpectedError()
                } else {
                    _registerResponse.value = Result.Error("Invalid Credentials")
                }
            } catch (e: Exception) {
                _registerResponse.value = Result.Error("Server Error plz try again")
            }
        }
    }

    private fun unexpectedError() {
        _registerResponse.value = Result.Error("Unexpected Error plz try again later")
    }
}