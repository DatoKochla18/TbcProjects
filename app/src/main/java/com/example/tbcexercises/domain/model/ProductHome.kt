package com.example.tbcexercises.domain.model

import com.example.tbcexercises.data.local.entity.FavouriteProductEntity


data class ProductHome(
    val productId: Int,
    val productName: String,
    val productImgUrl: String,
    val company: List<Company>,
    val productCategory: String,
    val productPrice: Double,
    val isFavourite: Boolean = false
) {
    fun toFavouriteProduct(): FavouriteProductEntity {
        return FavouriteProductEntity(
            productId = productId,
            productName = productName,
            productImgUrl = productImgUrl,
            company = company.joinToString(",") { it.name },
            productPrice = productPrice
        )
    }
}