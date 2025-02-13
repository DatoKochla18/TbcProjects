package com.example.tbcexercises.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tbcexercises.data.local.room.AppDatabase
import com.example.tbcexercises.data.local.room.entity.UserEntity
import com.example.tbcexercises.data.remote.apis.UserApi
import com.example.tbcexercises.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val database: AppDatabase,
    private val userApi: UserApi
) : UserRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getUsersPager(): Flow<PagingData<UserEntity>> {
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