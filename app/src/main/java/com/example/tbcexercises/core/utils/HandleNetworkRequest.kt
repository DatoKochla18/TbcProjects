package com.example.tbcexercises.core.utils

import com.example.tbcexercises.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> handleNetworkRequest(
    apiCall: suspend () -> Response<T>,
): Flow<Resource<T>> = flow {
    try {
        emit(Resource.Loading)
        val response = apiCall()

        if (response.isSuccessful) {
            response.body()?.let { data ->
                emit(Resource.Success(data))
            } ?: emit(Resource.Error(""))
        } else {
            emit(Resource.Error(response.message()))
        }
    } catch (e: Exception) {
        emit(Resource.Error(e.localizedMessage ?: ""))
    }
}

fun <T, R> Resource<T>.mapData(transform: (T) -> R): Resource<R> = when (this) {
    is Resource.Success -> Resource.Success(transform(data))
    is Resource.Error -> Resource.Error(message)
    is Resource.Loading -> Resource.Loading
}