package com.example.tbcexercises.feature_user.domain.repository

import androidx.paging.PagingData
import com.example.tbcexercises.feature_user.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsersPager(): Flow<PagingData<UserEntity>>
}