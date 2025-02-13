package com.example.tbcexercises.domain.repository

import androidx.paging.PagingData
import com.example.tbcexercises.data.local.room.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsersPager(): Flow<PagingData<UserEntity>>
}