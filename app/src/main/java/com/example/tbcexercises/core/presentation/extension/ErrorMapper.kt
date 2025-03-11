package com.example.tbcexercises.core.presentation.extension

import com.example.tbcexercises.R
import com.example.tbcexercises.core.domain.util.ErrorTypes

fun ErrorTypes.toMap(): Int {
    return when (this) {
        ErrorTypes.BLANK_FIELD -> R.string.field_can_not_be_blank
        ErrorTypes.INVALID_EMAIL -> R.string.invalid_email
        ErrorTypes.SHORT_PASSWORD -> R.string.the_password_needs_to_consist_of_at_least_8_characters
        ErrorTypes.INVALID_PASSWORD -> R.string.the_password_needs_to_contain_at_least_one_letter_and_digit
        ErrorTypes.INVALID_REPEAT_PASSWORD -> R.string.password_should_be_same
        ErrorTypes.NON_VALIDATED -> R.string.empty
    }
}