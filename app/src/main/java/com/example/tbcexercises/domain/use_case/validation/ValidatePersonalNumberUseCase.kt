package com.example.tbcexercises.domain.use_case.validation

import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.PersonalNumberError
import javax.inject.Inject

class ValidatePersonalNumberUseCase @Inject constructor() {
    operator fun invoke(personalNumber: String): Resource<Unit, PersonalNumberError> {
        if (personalNumber.any { !it.isDigit() }) return Resource.Error(PersonalNumberError.NotAllDigits)
        if (personalNumber.length != 9) return Resource.Error(PersonalNumberError.MismatchSize)

        return Resource.Success(Unit)
    }
}