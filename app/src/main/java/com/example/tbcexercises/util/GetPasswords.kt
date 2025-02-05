package com.example.tbcexercises.util

import com.example.tbcexercises.data.model.Password

fun getPasswords(): List<Password> {
    return listOf(
        Password(1),
        Password(2),
        Password(3),
        Password(4)
    )
}