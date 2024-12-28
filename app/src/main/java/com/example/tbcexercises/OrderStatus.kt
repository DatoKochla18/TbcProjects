package com.example.tbcexercises

data class OrderStatus(
    val id: Int,
    val name: String,
    val color: Int?,
    var isSelected: Boolean = false
)
