package com.example.tbcexercises.domain.model

data class Location(
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    val location: String,
    val reactionCount: Int,
    val rate: Int?
)