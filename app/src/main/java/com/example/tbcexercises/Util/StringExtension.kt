package com.example.tbcexercises.Util



fun String.isNumber():Boolean {
    return this.all { it.isDigit() }
}