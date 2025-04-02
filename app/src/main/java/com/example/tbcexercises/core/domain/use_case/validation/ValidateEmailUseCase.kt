package com.example.tbcexercises.core.domain.use_case.validation

import com.example.tbcexercises.core.domain.extension.isEmailValid
import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.core.domain.util.error.EmailError
import javax.inject.Inject

class ValidateEmailUseCase@Inject constructor() {
    operator fun invoke(email: String): Result<Unit, EmailError> {
        if (email.isBlank()) {
            return Result.Error(EmailError.BLANK_FIELD)
        }

        if (!email.isEmailValid()) {
            return Result.Error(EmailError.INVALID_EMAIL)
        }
        return Result.Success(Unit)
    }
}