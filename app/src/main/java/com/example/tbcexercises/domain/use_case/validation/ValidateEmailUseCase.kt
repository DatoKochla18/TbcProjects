package com.example.tbcexercises.domain.use_case.validation

import com.example.tbcexercises.domain.extension.isEmailValid
import com.example.tbcexercises.domain.util.ValidationResult

class ValidateEmailUseCase {
    operator fun invoke(email: String): ValidationResult {
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