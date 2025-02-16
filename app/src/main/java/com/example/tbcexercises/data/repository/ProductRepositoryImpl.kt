package com.example.tbcexercises.data.repository

import com.example.tbcexercises.common.Resource
import com.example.tbcexercises.common.handleNetworkRequest
import com.example.tbcexercises.data.remote.apis.ProductApi
import com.example.tbcexercises.data.remote.dtos.toProduct
import com.example.tbcexercises.domain.model.Product
import com.example.tbcexercises.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val productApi: ProductApi) : ProductRepository {
    override fun getProducts(limit: Int): Flow<Resource<List<Product>>> {
        return handleNetworkRequest { productApi.getProducts(limit) }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(resource.data.map { it.toProduct() })
                    is Resource.Error -> Resource.Error(resource.message)
                    is Resource.Loading -> Resource.Loading
                }
            }
    }


    override suspend fun getProduct(id: Int): Flow<Resource<Product>> {
        return handleNetworkRequest { productApi.getProduct(id) }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(resource.data.toProduct())
                    is Resource.Error -> Resource.Error(resource.message)
                    is Resource.Loading -> Resource.Loading
                }
            }
    }
}
