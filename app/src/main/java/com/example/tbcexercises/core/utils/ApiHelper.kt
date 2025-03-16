package com.example.tbcexercises.core.utils

import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.core.domain.util.RootError
import com.example.tbcexercises.core.domain.util.error.NetworkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class ApiHelper @Inject constructor() {
    suspend fun <T> handleNetworkRequestAsSuspend(
        apiCall: suspend () -> Response<T>,
    ): Result<T, NetworkError> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    Result.Success(data)
                } ?: Result.Error(NetworkError.EmptyResponse)
            } else {
                val error = when (response.code()) {
                    401 -> NetworkError.InvalidCredentials
                    404 -> NetworkError.UserNotFound
                    else -> NetworkError.HttpError(response.code(), response.errorBody()?.string())
                }
                Result.Error(error)
            }
        } catch (e: IOException) {
            Result.Error(NetworkError.ConnectionError)
        } catch (e: Exception) {
            Result.Error(NetworkError.ServerError(e))
        }
    }

    fun <T> handleNetworkRequestAsFlow(
        apiCall: suspend () -> Response<T>,
    ): Flow<Result<T, NetworkError>> = flow {
        emit(handleNetworkRequestAsSuspend(apiCall))
    }
}

fun <T, R, E : RootError> Result<T, E>.mapData(transform: (T) -> R): Result<R, E> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> Result.Error(error)
}