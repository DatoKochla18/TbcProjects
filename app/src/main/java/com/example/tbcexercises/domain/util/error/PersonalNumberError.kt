package com.example.tbcexercises.domain.util.error

sealed class PersonalNumberError : RootError {
    data object NotAllDigits : PersonalNumberError()
    data object MismatchSize : PersonalNumberError()

}