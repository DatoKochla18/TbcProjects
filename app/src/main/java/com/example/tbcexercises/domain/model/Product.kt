package com.example.tbcexercises.domain.model

import com.example.tbcexercises.data.remote.Rating

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val category: String,
    val image: String,
    val rating: Rating
)