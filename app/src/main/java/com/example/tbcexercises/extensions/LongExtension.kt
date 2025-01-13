package com.example.tbcexercises.extensions

fun Long.toCardNumber(): String {
    val str = this.toString()
    return str.chunked(4).joinToString("    ")
}