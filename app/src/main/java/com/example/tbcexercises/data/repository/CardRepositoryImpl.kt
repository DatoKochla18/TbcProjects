package com.example.tbcexercises.data.repository

import android.util.Log
import com.example.tbcexercises.data.mapper.toDomain
import com.example.tbcexercises.data.remote.service.CardService
import com.example.tbcexercises.data.remote.util.ApiHelper
import com.example.tbcexercises.data.remote.util.mapData
import com.example.tbcexercises.domain.model.GetCard
import com.example.tbcexercises.domain.repository.CardRepository
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val cardService: CardService,
) : CardRepository {
    override suspend fun getCards(): Resource<List<GetCard>, NetworkError> {
        Log.d("executed","exteuced")
        return apiHelper.handleNetworkRequestAsSuspend { cardService.getCards() }
            .mapData { responses -> responses.map { it.toDomain() } }
    }
}