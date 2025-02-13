package com.example.tbcexercises.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.tbcexercises.data.local.room.AppDatabase
import com.example.tbcexercises.data.local.room.entity.RemoteKeyEntity
import com.example.tbcexercises.data.local.room.entity.UserEntity
import com.example.tbcexercises.data.mappers.toUserEntity
import com.example.tbcexercises.data.remote.apis.UserApi
import javax.inject.Inject


//Code copied from google code lab slight adjustment
private const val USER_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    private val userApi: UserApi,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: USER_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

            }
        }


        try {
            val apiResponse = userApi.getUsers(page)

            val users = apiResponse.data.map { it.toUserEntity() }
            val endOfPaginationReached = users.isEmpty()
            appDatabase.withTransaction {
                // clear all tables in the database
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
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { user ->
                // Get the remote keys of the last item retrieved
                appDatabase.remoteKeysDao().remoteKeyByUserId(user.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UserEntity>): RemoteKeyEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { user ->
                // Get the remote keys of the first items retrieved
                appDatabase.remoteKeysDao().remoteKeyByUserId(user.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UserEntity>
    ): RemoteKeyEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { userId ->
                appDatabase.remoteKeysDao().remoteKeyByUserId(userId)
            }
        }
    }
}
