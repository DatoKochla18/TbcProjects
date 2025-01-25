package com.example.tbcexercises.presentation.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.R
import com.example.tbcexercises.data.model.request.AuthRequest
import com.example.tbcexercises.data.model.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginResponse = MutableStateFlow(LoginState())
    val loginResponse: StateFlow<LoginState> = _loginResponse

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginResponse.update { it.copy(loading = true, success = null, error = null) }
            try {
                val response = LoginService.login(
                    authRequest = AuthRequest(
                        email = email,
                        password = password
                    )
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        _loginResponse.update {
                            it.copy(
                                loading = false,
                                success = response.body(),
                                error = null
                            )
                        }
                    } ?: unexpectedError()
                } else {
                    _loginResponse.update {
                        it.copy(
                            loading = false,
                            success = null,
                            error = R.string.invalid_credentials
                        )
                    }
                }
            } catch (e: Exception) {
                _loginResponse.update {
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
        _loginResponse.update {
            it.copy(
                loading = false,
                success = null,
                error = R.string.unexpected_error_plz_try_again_later
            )

        }
    }
}