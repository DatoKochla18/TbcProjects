package com.example.tbcexercises

import android.content.Context

class Validation(private val context: Context) {
    private val resources = context.resources


    fun validateFullName(text:String):Result {
        return when {
            text.isEmpty() -> Result.Error(resources.getString(R.string.ValidEmptyFields))
            text.replace(" ","").any{it.isDigit() || !it.isLetter()} -> Result.Error(resources.getString(R.string.ValidFullNameContainingWrongCharacters))
            text.length <6 -> Result.Error(resources.getString(R.string.ValidFullNameShort))
            else -> Result.Success()
        }
    }


    fun validateEmail(text: String,emails:List<String> = listOf()): Result {
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
            text in emails -> Result.Error(resources.getString(R.string.ValidAlreadyUsedEmail))
            else -> Result.Success()
        }

    }


}
