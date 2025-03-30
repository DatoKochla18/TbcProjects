package com.example.tbcexercises.presentation.extension

import com.example.tbcexercises.R
import com.example.tbcexercises.domain.util.error.AccountNumberError
import com.example.tbcexercises.domain.util.error.NetworkError
import com.example.tbcexercises.domain.util.error.PersonalNumberError
import com.example.tbcexercises.domain.util.error.PhoneNumberError
import com.example.tbcexercises.domain.util.error.RootError


fun NetworkError.asStringResource(): Int {
    return when (this) {
        NetworkError.ConnectionError -> R.string.connection_error_plz_try_again
        NetworkError.EmptyResponse -> R.string.empty_response_try_again
        is NetworkError.HttpError -> R.string.http_error_something_went_wrong
        is NetworkError.ServerError -> R.string.nikas_backend_aint_backing
    }
}

fun AccountNumberError.asStringResource(): Int {
    return when (this) {
        AccountNumberError.InvalidLength -> R.string.it_should_be_23_charachter_long
    }
}

fun PersonalNumberError.asStringResource(): Int {
    return when (this) {
        PersonalNumberError.MismatchSize -> R.string.personal_number_must_be_11_charachter_long
        PersonalNumberError.NotAllDigits -> R.string.it_should_be_all_digits
    }
}

fun PhoneNumberError.asStringResource(): Int {
    return when (this) {
        PhoneNumberError.MismatchSize -> R.string.it_should_be_9_charachters_long
        PhoneNumberError.NotAllDigits -> R.string.it_should_be_all_digits
    }
}

fun RootError.asStringResource(): Int? {
    return when (this) {
        is AccountNumberError -> this.asStringResource()
        is PersonalNumberError -> this.asStringResource()
        is PhoneNumberError -> this.asStringResource()
        else -> null
    }
}