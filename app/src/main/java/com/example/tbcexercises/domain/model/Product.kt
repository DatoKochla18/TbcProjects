package com.example.tbcexercises.domain.model

import com.example.tbcexercises.data.remote.dtos.Rating

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)