package com.example.tbcexercises.presentation.model

data class Category(
    val id: String,
    val name: String,
    val children: List<Category>,
    val depth: Int = 0,
)