package com.example.tbcexercises.domain.util.error

import com.example.tbcexercises.domain.util.RootError

sealed class NetworkError : RootError {
    data object ConnectionError : NetworkError()
    data class ServerError(val exception: Exception) : NetworkError()
    data object EmptyResponse : NetworkError()
    data object UnknownError : NetworkError()

}