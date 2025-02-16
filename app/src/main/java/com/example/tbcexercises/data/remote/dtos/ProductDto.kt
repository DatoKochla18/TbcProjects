package com.example.tbcexercises.data.remote.dtos

import com.example.tbcexercises.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val description :String,
    val category: String,
    val image: String,
    val rating: Rating
)

@Serializable
data class Rating(val rate: Double, val count: Int)

fun ProductDto.toProduct(): Product {
    return Product(
        id = this.id,
        title = this.title,
        price = this.price,
        category = this.category,
        image = this.image,
        rating = this.rating,
        description =  this.description
    )
}