package com.example.tbcexercises.util


sealed class Result {
    class OnSuccess(val profile: String) : Result()

    data class OnError(val error: String) : Result()
}