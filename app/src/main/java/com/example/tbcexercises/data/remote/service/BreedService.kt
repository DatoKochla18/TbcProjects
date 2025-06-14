package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.BreedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BreedService {

    @GET("breeds")
    suspend fun getBreeds(): Response<List<BreedResponse>>

    @GET("breeds/search")
    suspend fun searchBreeds(@Query("q") query: String): Response<List<BreedResponse>>
}