package com.example.tbcexercises.feature_register.domain.use_case

import com.example.tbcexercises.core.domain.util.Resource
import com.example.tbcexercises.core.domain.util.error.RepeatPasswordError
import javax.inject.Inject

class ValidateRepeatPasswordUseCase@Inject constructor() {
    operator fun invoke(
        password: String,
        repeatedPassword: String,
    ): Resource<Unit, RepeatPasswordError> {

        if (repeatedPassword.isEmpty()) {
            return Resource.Error(RepeatPasswordError.BLANK_FIELD)
        }

        if (password != repeatedPassword) {
            return Resource.Error(RepeatPasswordError.NO_MATCH)
        }
        return Resource.Success(Unit)
    }
}