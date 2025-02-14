package com.example.tbcexercises.data.remote.apis

import com.example.tbcexercises.data.remote.response.LocationResponse
import retrofit2.Response
import retrofit2.http.GET

interface LocationApi {
    @GET("6dffd14a-836f-4566-b024-bd41ace3a874")
    suspend fun getUser(): Response<List<LocationResponse>>
}