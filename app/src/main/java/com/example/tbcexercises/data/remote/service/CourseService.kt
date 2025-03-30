package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.CourseResponse
import retrofit2.Response
import retrofit2.http.GET

interface CourseService {
    @GET("dc5137c6-bf20-4a04-9528-59c374995133")
    suspend fun getCourse(): Response<CourseResponse>
}
