package com.example.tbcexercises

fun String.format(): String {
    return this.filter { it.isLetter() }.uppercase() + this.filter { it.isDigit() }
}