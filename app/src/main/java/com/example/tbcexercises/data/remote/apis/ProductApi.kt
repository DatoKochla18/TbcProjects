package com.example.tbcexercises.data.remote.apis

import com.example.tbcexercises.data.remote.dtos.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int): Response<List<ProductDto>>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Response<ProductDto>
}
