package com.example.tbcexercises.domain.repository.product

import com.example.tbcexercises.data.remote.response.FavouriteProductResponse
import com.example.tbcexercises.domain.model.FavouriteProduct
import com.example.tbcexercises.utils.network_helper.Resource
import kotlinx.coroutines.flow.Flow

interface FavouriteProductRepository {
    suspend fun insertFavouriteProduct(favouriteProduct: FavouriteProduct)
    suspend fun insertFavouriteProducts(favouriteProduct: List<FavouriteProduct>)

    suspend fun deleteFavouriteProduct(favouriteProduct: FavouriteProduct)
    fun getAllFavouriteProducts(): Flow<Resource<List<FavouriteProduct>>>
    fun getAllFavouriteProductIds(): Flow<List<Int>>

    fun getFavouriteProductsFromServer(ids: String): Flow<Resource<List<FavouriteProductResponse>>>

    suspend fun saveFavouriteProducts()
}
