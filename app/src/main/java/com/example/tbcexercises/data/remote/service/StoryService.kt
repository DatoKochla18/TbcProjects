package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.StoryResponse
import retrofit2.Response
import retrofit2.http.GET


interface StoryService {

    @GET("00a18030-a8c7-47c4-b0c5-8bff92a29ebf")
    suspend  fun getStories(): Response<List<StoryResponse>>
}