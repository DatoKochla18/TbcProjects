package com.example.tbcexercises.core.utils

import com.example.tbcexercises.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T, R> handleNetworkRequest(
    apiCall: suspend () -> Response<T>,
    mapper: (T) -> R
): Flow<Resource<R>> = flow {
    try {
        emit(Resource.Loading)
        val response = apiCall()

        if (response.isSuccessful) {
            response.body()?.let { data ->
                emit(Resource.Success(mapper(data)))
            } ?: emit(Resource.Error(""))
        } else {
            emit(Resource.Error(response.message()))
        }
    } catch (e: Exception) {
        emit(Resource.Error(e.localizedMessage ?: ""))
    }
}