package com.example.tbcexercises.core.domain.use_case.validation

import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.core.domain.util.error.PasswordError


class ValidatePasswordUseCase {
    operator fun invoke(password: String): Result<Unit, PasswordError> {
        if (password.length < 8) {
            return Result.Error(PasswordError.SHORT_PASSWORD)
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return Result.Error(PasswordError.INVALID_PASSWORD)
        }
        return Result.Success(Unit)
    }


}