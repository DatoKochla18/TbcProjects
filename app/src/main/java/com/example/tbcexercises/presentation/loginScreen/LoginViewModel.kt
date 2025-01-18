package com.example.tbcexercises.presentation.loginScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.data.request.AuthRequest
import com.example.tbcexercises.data.response.LoginResponse
import com.example.tbcexercises.data.source.remote.RetrofitInstance
import com.example.tbcexercises.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(
) : ViewModel() {

    private val _loginResponse = MutableStateFlow<Response<LoginResponse>?>(null)
    val loginResponse: StateFlow<Response<LoginResponse>?> = _loginResponse

    private fun login(authRequest: AuthRequest, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.login(authRequest)
                _loginResponse.value = response
            } catch (e: Exception) {
                onError(e.message.toString())
            }
        }
    }

    fun validateFields(email: String, password: String, onException: (String) -> Unit): Result {
        if (email.isEmpty() || password.isEmpty()) {
            return Result.Error("Field must not be empty")
        }

        if (email == "eve.holt@reqres.in" && password.length > 6) {
            return Result.Success(
                login(
                    AuthRequest(email = email, password = password),
                    onException
                )
            )

        }

        if (password.length <= 6) return Result.Error("Password Length Should be Greater than 6")
        if (email != "eve.holt@reqres.in") return Result.Error("For Today Login only Works for there email eve.holt@reqres.in")

        return Result.Error("Unexpected Error Sorry Please Try Again")
    }

}
