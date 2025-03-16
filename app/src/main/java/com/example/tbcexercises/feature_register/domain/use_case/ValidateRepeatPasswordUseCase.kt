package com.example.tbcexercises.feature_register.domain.use_case

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