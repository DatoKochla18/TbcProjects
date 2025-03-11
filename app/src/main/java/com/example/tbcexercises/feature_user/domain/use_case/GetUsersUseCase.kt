package com.example.tbcexercises.feature_user.domain.use_case

import androidx.paging.PagingData
import androidx.paging.map
import com.example.tbcexercises.feature_user.data.mapper.toUser
import com.example.tbcexercises.feature_user.domain.model.User
import com.example.tbcexercises.feature_user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
     operator fun invoke(): Flow<PagingData<User>> {
        return userRepository.getUsersPager().map { pagingData ->
            pagingData.map { userEntity -> userEntity.toUser() }
        }
    }
}