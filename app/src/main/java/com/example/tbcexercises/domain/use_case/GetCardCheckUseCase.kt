package com.example.tbcexercises.domain.use_case

import com.example.tbcexercises.domain.model.GetCardCheckStatus
import com.example.tbcexercises.domain.repository.CardCheckRepository
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import javax.inject.Inject

class GetCardCheckUseCase @Inject constructor(
    private val cardCheckRepository: CardCheckRepository,
) {
    suspend operator fun invoke(): Resource<GetCardCheckStatus, NetworkError> {
        return cardCheckRepository.getCardCheckStatus()
    }
}