package com.example.tbcexercises.utils

//Code copied from here  https://developermemos.com/posts/validating-email-addresses-kotlin
//little adjust
fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    return this.matches(emailRegex.toRegex())
}