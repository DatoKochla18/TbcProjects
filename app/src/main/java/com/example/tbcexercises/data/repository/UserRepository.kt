package com.example.tbcexercises.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tbcexercises.data.local.AppDatabase
import com.example.tbcexercises.data.local.entity.UserEntity
import com.example.tbcexercises.data.remote.apis.UserApi
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val database: AppDatabase,
    private val userApi: UserApi
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getUsersPager(): Flow<PagingData<UserEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                prefetchDistance = 1
            ),
            remoteMediator = UserRemoteMediator(userApi, database),
            pagingSourceFactory = { database.usersDao().getUsers() }
        ).flow
    }
}
