package com.example.tbcexercises.domain.util

import com.example.tbcexercises.domain.util.error.RootError


sealed interface Resource<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Resource<D, E>
    data class Error<out D, out E : RootError>(val error: E) : Resource<D, E>
}