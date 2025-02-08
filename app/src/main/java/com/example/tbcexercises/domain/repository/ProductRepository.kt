package com.example.tbcexercises.domain.repository

import com.example.tbcexercises.common.Resource
import com.example.tbcexercises.data.remote.ProductDto
import com.example.tbcexercises.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(limit: Int): Flow<Resource<List<Product>>>

    suspend fun getProduct(id: Int): ProductDto
}