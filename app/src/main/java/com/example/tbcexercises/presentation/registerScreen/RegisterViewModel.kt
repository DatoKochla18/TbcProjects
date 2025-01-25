package com.example.tbcexercises.presentation.registerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.R
import com.example.tbcexercises.data.model.request.AuthRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _registerResponse = MutableStateFlow(RegisterState())
    val registerResponse: StateFlow<RegisterState> = _registerResponse


    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _registerResponse.update { it.copy(loading = true, success = null, error = null) }
            try {
                val response = RegisterService.register(
                    authRequest = AuthRequest(
                        email = email,
                        password = password
                    )
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        _registerResponse.update {
                            it.copy(
                                loading = false,
                                success = response.body(),
                                error = null
                            )
                        }
                    } ?: unexpectedError()
                } else {
                    _registerResponse.update {
                        it.copy(
                            loading = false,
                            success = null,
                            error = R.string.invalid_credentials
                        )
                    }
                }
            } catch (e: Exception) {
                _registerResponse.update {
                    it.copy(
                        loading = false,
                        success = null,
                        error = R.string.server_error_plz_try_again
                    )
                }
            }
        }
    }

    private fun unexpectedError() {
        _registerResponse.update {
            it.copy(
                loading = false,
                success = null,
                error = R.string.unexpected_error
            )
        }
    }
}