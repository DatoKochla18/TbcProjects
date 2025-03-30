package com.example.tbcexercises.domain.util.error

sealed class AccountNumberError : RootError {
    object InvalidLength : AccountNumberError()

}