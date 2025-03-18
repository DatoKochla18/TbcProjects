package com.example.tbcexercises.data.remote.util

import com.example.tbcexercises.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ApiHelper @Inject constructor() {
    fun <T> handleNetworkRequest(apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        try {
            val response = apiCall()

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error(""))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }

}

fun <T, R> Resource<T>.mapper(map: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(map(this.data))
        is Resource.Error -> Resource.Error(this.message)
    }
}