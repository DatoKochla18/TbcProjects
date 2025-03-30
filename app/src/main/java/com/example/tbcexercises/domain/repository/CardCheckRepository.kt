package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.domain.model.GetCardCheckStatus
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.domain.util.error.NetworkError

interface CardCheckRepository {

    suspend fun getCardCheckStatus(): Resource<GetCardCheckStatus, NetworkError>
}