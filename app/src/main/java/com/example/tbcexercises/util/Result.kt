package com.example.tbcexercises.util

sealed class Result {
    class Success<T>(val response: T) : Result()

    data class Error(val error: String) : Result()
}