package com.example.tbcexercises.presentation.registerScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.data.request.AuthRequest
import com.example.tbcexercises.data.response.RegisterResponse
import com.example.tbcexercises.data.source.remote.RetrofitInstance
import com.example.tbcexercises.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _registerResponse = MutableStateFlow<Response<RegisterResponse>?>(null)
    val registerResponse: StateFlow<Response<RegisterResponse>?> = _registerResponse

    private fun register(authRequest: AuthRequest, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.register(authRequest)
                _registerResponse.value = response
            } catch (e: Exception) {
                onError(e.message.toString())
            }
        }
    }

    fun validateFields(
        email: String,
        password: String,
        username: String,
        onException: (String) -> Unit
    ): Result {
        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            return Result.Error("Field must not be empty")
        }

        if (email == "eve.holt@reqres.in" && password.length > 6) {
            return Result.Success(
                register(
                    AuthRequest(email = email, password = password),
                    onError = onException
                )
            )

        }

        if (password.length <= 6) return Result.Error("Password Length Should be Greater than 6")
        if (email != "eve.holt@reqres.in") return Result.Error("For Today Register only Works for there email eve.holt@reqres.in")

        return Result.Error("Unexpected Error Sorry Please Try Again")
    }
}