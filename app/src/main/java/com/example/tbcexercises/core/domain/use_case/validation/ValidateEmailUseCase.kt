package com.example.tbcexercises.core.domain.use_case.validation

import com.example.tbcexercises.core.domain.extension.isEmailValid
import com.example.tbcexercises.core.domain.util.ErrorTypes
import com.example.tbcexercises.core.domain.util.ValidationResult

class ValidateEmailUseCase {
    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = ErrorTypes.BLANK_FIELD
            )
        }

        if (!email.isEmailValid()) {
            return ValidationResult(
                successful = false,
                errorMessage = ErrorTypes.INVALID_EMAIL
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}