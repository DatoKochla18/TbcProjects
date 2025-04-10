package com.example.tbcexercises.core.data.remote.utils

import com.example.tbcexercises.core.domain.util.Resource
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
    ): Resource<T, NetworkError> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    Resource.Success(data)
                } ?: Resource.Error(NetworkError.EmptyResponse)
            } else {
                val error = when (response.code()) {
                    401 -> NetworkError.InvalidCredentials
                    404 -> NetworkError.UserNotFound
                    else -> NetworkError.HttpError(response.code(), response.errorBody()?.string())
                }
                Resource.Error(error)
            }
        } catch (e: IOException) {
            Resource.Error(NetworkError.ConnectionError)
        } catch (e: Exception) {
            Resource.Error(NetworkError.ServerError(e))
        }
    }

    fun <T> handleNetworkRequestAsFlow(
        apiCall: suspend () -> Response<T>,
    ): Flow<Resource<T, NetworkError>> = flow {
        emit(handleNetworkRequestAsSuspend(apiCall))
    }
}

fun <T, R, E : RootError> Resource<T, E>.mapData(transform: (T) -> R): Resource<R, E> = when (this) {
    is Resource.Success -> Resource.Success(transform(data))
    is Resource.Error -> Resource.Error(error)
}