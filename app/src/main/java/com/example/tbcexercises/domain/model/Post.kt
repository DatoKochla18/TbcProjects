package com.example.tbcexercises.domain.model


data class Post(
    val id: Int,
    val images: List<String>? = null,
    val title: String,
    val comments: String,
    val likes: String,
    val shareContent: String,
    val owner: Owner,
)
