package com.example.tbcexercises.core.domain.use_case.validation

import com.example.tbcexercises.core.domain.extension.isEmailValid
import com.example.tbcexercises.core.domain.util.Resource
import com.example.tbcexercises.core.domain.util.error.EmailError
import javax.inject.Inject

class ValidateEmailUseCase@Inject constructor() {
    operator fun invoke(email: String): Resource<Unit, EmailError> {
        if (email.isBlank()) {
            return Resource.Error(EmailError.BLANK_FIELD)
        }

        if (!email.isEmailValid()) {
            return Resource.Error(EmailError.INVALID_EMAIL)
        }
        return Resource.Success(Unit)
    }
}