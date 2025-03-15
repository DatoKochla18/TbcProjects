package com.example.tbcexercises.core.domain.use_case.validation

import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.core.domain.util.error.RepeatPasswordError

class ValidateRepeatPasswordUseCase {
    operator fun invoke(
        password: String,
        repeatedPassword: String,
    ): Result<Unit, RepeatPasswordError> {

        if (repeatedPassword.isEmpty()) {
            return Result.Error(RepeatPasswordError.BLANK_FIELD)
        }

        if (password != repeatedPassword) {
            return Result.Error(RepeatPasswordError.NO_MATCH)
        }
        return Result.Success(Unit)
    }
}