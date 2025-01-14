package com.example.tbcexercises.utils

sealed class Result {

    class OnSuccess() : Result()

    data class OnError(val error: String) : Result()
}