package com.example.tbcexercises.utils.exntension

//Code copied from  https://medium.com/@kalpeshdoru/10-kotlin-extension-functions-for-input-validation-5776c6139e8f
fun String.isEmailValid(): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return emailRegex.toRegex().matches(this)
}