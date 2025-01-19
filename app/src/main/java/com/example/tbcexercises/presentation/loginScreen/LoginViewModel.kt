package com.example.tbcexercises.presentation.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.data.request.AuthRequest
import com.example.tbcexercises.data.response.LoginResponse
import com.example.tbcexercises.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginViewModel(
) : ViewModel() {

    private val _loginResponse = MutableStateFlow<Response<LoginResponse>?>(null)
    //val loginResponse: StateFlow<Response<LoginResponse>?> = _loginResponse
    // Normally you do this but it is not needed here

    fun login(
        authRequest: AuthRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = LoginService.login(authRequest)
                _loginResponse.value = response
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) { onSuccess() }
                } else {
                    withContext(Dispatchers.Main) {
                        onError(response.message())
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e.message.toString())
                }
            }
        }
    }

    fun validateFields(email: String, password: String): Result {
        if (email.isEmpty() || password.isEmpty()) {
            return Result.Error("Field must not be empty")
        }

        if (password.length <= 6) return Result.Error("Password Length Should be Greater than 6")
        if (email != "eve.holt@reqres.in") return Result.Error("For Today Login only Works for there email eve.holt@reqres.in")

        return Result.Success()
    }

}
