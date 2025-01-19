package com.example.tbcexercises.util

sealed class Result {
    class Success() : Result()

    data class Error(val error: String) : Result()
}