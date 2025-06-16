package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.ImageDetailResponse
import com.example.tbcexercises.data.remote.response.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ImageService {

    @GET("colors/new")
    suspend fun getImages(
        @Query("format") format: String = JSON
    ): Response<List<ImageResponse>>

    @GET("colors")
    suspend fun searchImages(
        @Query("keywords") keywords: String,
        @Query("format") format: String = JSON
    ): Response<List<ImageResponse>>

    @GET("colors")
    suspend fun getImage(
        @Query("hex") hex: String,
        @Query("format") format: String = JSON
    ): Response<List<ImageDetailResponse>>

    companion object {
        const val JSON = "json"
    }
}
