package com.example.tbcexercises.presentation.registerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.data.request.AuthRequest
import com.example.tbcexercises.data.response.RegisterResponse
import com.example.tbcexercises.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _registerResponse = MutableStateFlow<Response<RegisterResponse>?>(null)
    // val registerResponse: StateFlow<Response<RegisterResponse>?> = _registerResponse

    fun register(
        authRequest: AuthRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RegisterService.register(authRequest)
                _registerResponse.value = response
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

    fun validateFields(
        email: String,
        password: String,
        username: String
    ): Result {
        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            return Result.Error("Field must not be empty")
        }

        if (email != "eve.holt@reqres.in") {
            return Result.Error("For today, registration only works for the email: eve.holt@reqres.in")
        }

        if (password.length <= 6) {
            return Result.Error("Password length should be greater than 6")
        }

        return Result.Success()
    }
}