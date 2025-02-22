package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.PostResponse
import retrofit2.Response
import retrofit2.http.GET

interface PostService {

    @GET("1ba8b612-8391-41e5-8560-98e4a48decc7")
    suspend fun getPosts() : Response<List<PostResponse>>
}