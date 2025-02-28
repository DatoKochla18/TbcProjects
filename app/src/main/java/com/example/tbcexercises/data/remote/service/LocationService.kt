package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.LocationResponse
import retrofit2.Response
import retrofit2.http.GET

interface LocationService {

    @GET("c4c64996-4ed9-4cbc-8986-43c4990d495a")
    suspend fun getLocations(): Response<List<LocationResponse>>
}