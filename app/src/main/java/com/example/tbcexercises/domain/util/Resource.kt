package com.example.tbcexercises.domain.util

interface RootError

sealed interface Resource<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Resource<D, E>
    data class Error<out D, out E : RootError>(val error: E) : Resource<D, E>
}


fun <T, R, E : RootError> Resource<T, E>.mapData(transform: (T) -> R): Resource<R, E> =
    when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(error)
    }