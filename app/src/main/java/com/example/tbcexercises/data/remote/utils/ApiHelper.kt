package com.example.tbcexercises.data.remote.utils

import android.util.Log
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class ApiHelper @Inject constructor() {

    companion object {
        private const val TAG = "ApiHelper"
    }

    suspend fun <T> handleHttpRequest(
        apiCall: suspend () -> Response<T>,
    ): Resource<T, NetworkError> {
        return try {
            Log.d(TAG, "Starting API request...")
            val response = apiCall()

            Log.d(
                TAG,
                "API response received: isSuccessful=${response.isSuccessful}, code=${response.code()}"
            )

            if (response.isSuccessful) {
                response.body()?.let { data ->
                    Log.d(TAG, "API success with data: $data")
                    Resource.Success(data)
                } ?: run {
                    Log.e(TAG, "API success but response body is null")
                    Resource.Error(NetworkError.EmptyResponse)
                }
            } else {
                Log.e(TAG, "API failed with code=${response.code()}, message=${response.message()}")
                Resource.Error(NetworkError.UnknownError)
            }

        } catch (e: IOException) {
            Log.e(TAG, "Connection error occurred: ${e.localizedMessage}", e)
            Resource.Error(NetworkError.ConnectionError)
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected server error: ${e.localizedMessage}", e)
            Resource.Error(NetworkError.ServerError(e))
        }
    }
}
