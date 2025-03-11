package com.example.tbcexercises.core.domain.use_case.validation

import com.example.tbcexercises.core.domain.util.ErrorTypes
import com.example.tbcexercises.core.domain.util.ValidationResult

class ValidatePasswordUseCase {
    operator fun invoke(password: String): ValidationResult {
        if(password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = ErrorTypes.SHORT_PASSWORD
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if(!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = ErrorTypes.INVALID_PASSWORD
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}