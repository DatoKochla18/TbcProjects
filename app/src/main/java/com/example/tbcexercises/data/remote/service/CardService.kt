package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.CardResponse
import retrofit2.Response
import retrofit2.http.GET

interface CardService {

    @GET("d689fe3e-6faf-446a-9896-c538de3449fa")
   suspend fun getCards(): Response<List<CardResponse>>
}