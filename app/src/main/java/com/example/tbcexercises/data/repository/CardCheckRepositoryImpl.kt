package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.mapper.toDomain
import com.example.tbcexercises.data.remote.service.CardCheckService
import com.example.tbcexercises.data.remote.util.ApiHelper
import com.example.tbcexercises.data.remote.util.mapData
import com.example.tbcexercises.domain.model.GetCardCheckStatus
import com.example.tbcexercises.domain.repository.CardCheckRepository
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import javax.inject.Inject

class CardCheckRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val cardCheckService: CardCheckService,
) : CardCheckRepository {
    override suspend fun getCardCheckStatus(): Resource<GetCardCheckStatus, NetworkError> {
        return apiHelper.handleNetworkRequestAsSuspend { cardCheckService.getCardCheckStatus() }
            .mapData {
                it.toDomain()
            }
    }
}