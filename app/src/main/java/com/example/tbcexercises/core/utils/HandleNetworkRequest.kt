package com.example.tbcexercises.core.utils

import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.core.domain.util.RootError
import com.example.tbcexercises.core.domain.util.error.NetworkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.Response

fun <T> handleNetworkRequest(
    apiCall: suspend () -> Response<T>,
): Flow<Result<T, NetworkError>> = flow {
    try {
        val response = apiCall()

        if (response.isSuccessful) {
            response.body()?.let { data ->
                emit(Result.Success(data))
            } ?: emit(Result.Error(NetworkError.EmptyResponse))
        } else {
            val error = when (response.code()) {
                401 -> NetworkError.InvalidCredentials
                404 -> NetworkError.UserNotFound
                else -> NetworkError.HttpError(response.code(), response.errorBody()?.string())
            }
            emit(Result.Error(error))
        }
    } catch (e: IOException) {
        emit(Result.Error(NetworkError.ConnectionError))
    } catch (e: Exception) {
        emit(Result.Error(NetworkError.ServerError(e)))
    }
}

fun <T, R, E : RootError> Result<T, E>.mapData(transform: (T) -> R): Result<R, E> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> Result.Error(error)
}