package com.example.tbcexercises.feature_user.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.tbcexercises.core.data.local.AppDatabase
import com.example.tbcexercises.feature_user.data.mapper.toDomain
import com.example.tbcexercises.feature_user.data.mediator.UserRemoteMediator
import com.example.tbcexercises.feature_user.data.remote.service.UserApi
import com.example.tbcexercises.feature_user.domain.model.GetUser
import com.example.tbcexercises.feature_user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val userApi: UserApi,
) : UserRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getUsersPager(): Flow<PagingData<GetUser>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                prefetchDistance = 1
            ),
            remoteMediator = UserRemoteMediator(userApi, database),
            pagingSourceFactory = { database.usersDao().getUsers() }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }
}