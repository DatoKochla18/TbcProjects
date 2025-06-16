package com.example.tbcexercises.domain.util

interface RootError

sealed interface Resource<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Resource<D, E>
    data class Error<out D, out E : RootError>(val error: E) : Resource<D, E>
}

inline fun <D, R, E : RootError> Resource<D, E>.map(transform: (D) -> R): Resource<R, E> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(error)
    }
}

inline fun <D, R, E : RootError> Resource<List<D>, E>.mapList(transform: (D) -> R): Resource<List<R>, E> {
    return when (this) {
        is Resource.Success -> Resource.Success(data.map(transform))
        is Resource.Error -> Resource.Error(error)
    }
}