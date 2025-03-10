package com.example.tbcexercises.domain.use_case.validation

import com.example.tbcexercises.domain.util.ValidationResult

class ValidateRepeatPasswordUseCase {
    operator fun invoke(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}