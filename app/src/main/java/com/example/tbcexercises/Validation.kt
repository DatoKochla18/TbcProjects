package com.example.tbcexercises

import android.content.Context

class Validation(private val context: Context) {
    private val resources = context.resources


    fun validateAge(text: String): Result {
        return when {
            text.isEmpty() -> Result.Error(resources.getString(R.string.ValidEmptyFields))
            (text.toInt() < 0) -> Result.Error(resources.getString(R.string.ValidAgeNegative))

            (text.toInt() > 150) -> Result.Error(resources.getString(R.string.ValidAgeOld))

            else -> return Result.Success()
        }

    }

    fun validateFirstName(text: String): Result {
        return when {
            text.isEmpty() -> Result.Error(resources.getString(R.string.ValidEmptyFields))
            text.any { !it.isLetter() } -> Result.Error(resources.getString(R.string.ValidFirstAndLastNameOnlyLetters))

            text.length ==1 -> Result.Error(resources.getString(R.string.ValidFirstAndLastNameLength))
            else -> Result.Success()
        }


    }

    fun validateLastName(text: String): Result {
        return when {
            text.isEmpty() -> Result.Error(resources.getString(R.string.ValidEmptyFields))
            text.any { !it.isLetter() } -> Result.Error(resources.getString(R.string.ValidFirstAndLastNameOnlyLetters))
            text.length ==1 -> Result.Error(resources.getString(R.string.ValidFirstAndLastNameLength))
            else -> Result.Success()
        }


    }

    fun validateUserName(text: String): Result {
        return when {
            text.isEmpty() -> Result.Error(resources.getString(R.string.ValidEmptyFields))
            text.length < 10 -> Result.Error(resources.getString(R.string.ValidUserNameLength))
            else -> return Result.Success()
        }


    }


    fun validateEmail(text: String): Result {
        return when {
            text.isEmpty() -> Result.Error(resources.getString(R.string.ValidEmptyFields))
            text[0].toString() == "." || text[text.length - 1].toString() == "." -> Result.Error(
                resources.getString(R.string.ValidEmailDots)
            )
            text.any { it in listOf('+', '-', '&', '|', '!', '(', ')', '{', '}', '[', ']', '^', '~', '*', '?', ':') }
            -> Result.Error(resources.getString(R.string.ValidEmailCantContainSpecialChars))
            !text.contains("@") ->  Result.Error(resources.getString(R.string.ValidEmailNotContainingSymbol))
            text.filter { it == '@' }
                .count() > 1 -> Result.Error(resources.getString(R.string.ValidEmailContainingMoreSymbol))
            !text.contains(".") -> Result.Error(resources.getString(R.string.ValidEmailShouldContainDot))
            text.substring(text.lastIndexOf("."),text.length).any{it.isDigit()} -> Result.Error(resources.getString(R.string.ValidEmailDomain))
            text.contains(" ") -> Result.Error(resources.getString(R.string.ValidEmailSpaces))
            text.indexOf("@") > text.lastIndexOf(".") -> Result.Error(resources.getString(R.string.ValidEmailMissingDots))
            text.contains("@.") ->  Result.Error(resources.getString(R.string.ValidEmailContainingSymbolAndDotConsecutive))

            text.contains("..") -> Result.Error(resources.getString(R.string.ValidEmailConsecutiveDots))
            else -> Result.Success()
        }

    }
}