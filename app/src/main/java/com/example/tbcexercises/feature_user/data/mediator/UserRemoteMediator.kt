package com.example.tbcexercises.feature_user.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.tbcexercises.core.data.local.AppDatabase
import com.example.tbcexercises.feature_user.data.local.entity.RemoteKeyEntity
import com.example.tbcexercises.feature_user.data.local.entity.UserEntity
import com.example.tbcexercises.feature_user.data.mapper.toUserEntity
import com.example.tbcexercises.feature_user.data.remote.service.UserApi
import javax.inject.Inject

//Code copied from google code lab slight adjustment
private const val USER_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    private val userApi: UserApi,
    private val appDatabase: AppDatabase,
) : RemoteMediator<Int, UserEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>,
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: USER_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

            }
        }


        try {
            val apiResponse = userApi.getUsers(page)

            val users = apiResponse.data.map { it.toUserEntity() }
            val endOfPaginationReached = users.isEmpty()
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.remoteKeysDao().clearRemoteKeys()
                    appDatabase.usersDao().clearUsers()
                }
                val prevKey = if (page == USER_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = users.map {
                    RemoteKeyEntity(userId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                appDatabase.remoteKeysDao().insertAll(keys)
                appDatabase.usersDao().insertAll(users)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UserEntity>): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { user ->
                appDatabase.remoteKeysDao().remoteKeyByUserId(user.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UserEntity>): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { user ->
                appDatabase.remoteKeysDao().remoteKeyByUserId(user.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UserEntity>,
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { userId ->
                appDatabase.remoteKeysDao().remoteKeyByUserId(userId)
            }
        }
    }
}