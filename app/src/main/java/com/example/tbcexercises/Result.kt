package com.example.tbcexercises

sealed class Result() {

    class Success : Result()


    data class Error(val error: String) : Result()
}