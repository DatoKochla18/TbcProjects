package com.example.tbcexercises.domain.use_case.validation

import android.util.Log
import com.example.tbcexercises.domain.extension.isEmailValid
import com.example.tbcexercises.domain.util.ValidationResult

class ValidateEmailUseCase {
    operator fun invoke(email: String): ValidationResult {
        Log.d("email", email)
        Log.d("emailisvalid", email.isEmailValid().toString())
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }

        if (!email.isEmailValid()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid email"
            )
        }
        return ValidationResult(
            successful = true
        )
    }

}