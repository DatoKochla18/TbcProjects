package com.example.tbcexercises.domain.model


data class GetCategories(
    val id: String,
    val name: String,
    val children: List<GetCategories>,
    val depth: Int = 0,
)