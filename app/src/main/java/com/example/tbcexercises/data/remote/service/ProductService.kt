package com.example.tbcexercises.data.remote.service

import com.example.tbcexercises.data.remote.response.home.HomePaginatedResponse
import com.example.tbcexercises.data.remote.response.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("products/lowest_priced")
    suspend fun getHomeProducts(
        @Query("page") page: Int, @Query("perPage") perPage: Int
    ): HomePaginatedResponse

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id : Int) : Response<List<ProductResponse>>


}