package com.example.tbcexercises.presentation.mapper

import com.example.tbcexercises.R
import com.example.tbcexercises.domain.util.error.NetworkError

fun NetworkError.asStringResource():Int{
    return when(this){
        NetworkError.ConnectionError -> R.string.connection_error
        NetworkError.EmptyResponse -> R.string.empty_response
        is NetworkError.ServerError -> R.string.server_error
        NetworkError.UnknownError -> R.string.unknown_error
    }
}