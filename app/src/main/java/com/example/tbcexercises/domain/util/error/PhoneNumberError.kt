package com.example.tbcexercises.domain.util.error

sealed class PhoneNumberError : RootError {
    data object NotAllDigits : PhoneNumberError()
    data object MismatchSize : PhoneNumberError()
}