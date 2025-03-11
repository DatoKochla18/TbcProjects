package com.example.tbcexercises.core.domain.use_case.validation

import com.example.tbcexercises.core.domain.util.ErrorTypes
import com.example.tbcexercises.core.domain.util.ValidationResult

class ValidateRepeatPasswordUseCase {
    operator fun invoke(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = ErrorTypes.INVALID_REPEAT_PASSWORD
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}