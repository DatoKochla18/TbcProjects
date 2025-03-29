package com.example.tbcexercises.data.remote.util

import android.util.Log
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import com.example.tbcexercises.domain.util.error.RootError
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
                    else -> NetworkError.HttpError(response.code(), response.errorBody()?.string())
                }
                Resource.Error(error)
            }
        } catch (e: IOException) {
            Resource.Error(NetworkError.ConnectionError)
        } catch (e: Exception) {
            Log.d("exception", e.message.toString())
            Resource.Error(NetworkError.ServerError(e))
        }
    }
}

fun <T, R, E : RootError> Resource<T, E>.mapData(transform: (T) -> R): Resource<R, E> =
    when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(error)
    }