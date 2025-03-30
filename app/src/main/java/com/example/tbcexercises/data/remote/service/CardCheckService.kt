package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.CardCheckResponse
import retrofit2.Response
import retrofit2.http.GET

interface CardCheckService {

    @GET("29d002d4-3ccd-4eaa-95eb-a9d1601ce123")
    suspend fun getCardCheckStatus(): Response<CardCheckResponse>

}