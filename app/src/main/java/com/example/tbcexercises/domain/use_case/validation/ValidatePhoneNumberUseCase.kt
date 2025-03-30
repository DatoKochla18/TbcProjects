package com.example.tbcexercises.domain.use_case.validation

import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.PhoneNumberError
import javax.inject.Inject

class ValidatePhoneNumberUseCase @Inject constructor() {
    operator fun invoke(phone: String): Resource<Unit, PhoneNumberError> {
        if (phone.any { !it.isDigit() }) return Resource.Error(PhoneNumberError.NotAllDigits)
        if (phone.length != 9) return Resource.Error(PhoneNumberError.MismatchSize)

        return Resource.Success(Unit)
    }
}