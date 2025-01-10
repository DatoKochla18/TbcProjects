package com.example.tbcexercises.model

import java.util.UUID

data class Order(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val img: Int,
    val quantity: Int,
    val price: Float,
    val orderStatus: OrderStatus,
    val orderColor: OrderColor,
    var ratingStars: Int? = null,
    var ratingText: String? = null
)
