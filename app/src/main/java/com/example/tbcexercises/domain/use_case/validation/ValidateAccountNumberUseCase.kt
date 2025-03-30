package com.example.tbcexercises.domain.use_case.validation

import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.AccountNumberError
import javax.inject.Inject

class ValidateAccountNumberUseCase @Inject constructor() {
    operator fun invoke(accountNumber:String):Resource<Unit,AccountNumberError> {
        if (accountNumber.length != 22) {
            return Resource.Error(AccountNumberError.InvalidLength)
        }
        return Resource.Success(Unit)
    }
}