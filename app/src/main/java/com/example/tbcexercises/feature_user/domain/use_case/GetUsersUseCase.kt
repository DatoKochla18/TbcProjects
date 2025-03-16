package com.example.tbcexercises.feature_user.domain.use_case

import androidx.paging.PagingData
import com.example.tbcexercises.feature_user.domain.model.GetUser
import com.example.tbcexercises.feature_user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<PagingData<GetUser>> {
        return userRepository.getUsersPager()
    }
}
