package com.example.tbcexercises.data.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tbcexercises.data.model.User
import com.example.tbcexercises.data.model.response.UserResponse
import com.example.tbcexercises.data.network.RetrofitInstance
import kotlinx.coroutines.delay
import retrofit2.Response

class UserPagingSource : PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val response: Response<UserResponse> = RetrofitInstance.api.fetchUsers(page)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    delay(2000)//without this i cant see loading on end of paging
                    // i know this is bad but it is for experiment only
                    LoadResult.Page(
                        data = body.data,
                        prevKey = null,
                        nextKey = if (page < body.totalPages) page + 1 else null
                    )
                } else {
                    LoadResult.Error(Exception())

                }
            } else {
                LoadResult.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
