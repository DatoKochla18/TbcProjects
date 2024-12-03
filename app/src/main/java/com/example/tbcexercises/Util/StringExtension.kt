package com.example.tbcexercises.Util



fun String.isNumber():Boolean {
    return this.all { it.isDigit() } && this.get(0)!='0'
}