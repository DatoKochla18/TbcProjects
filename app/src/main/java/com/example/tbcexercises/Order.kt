package com.example.tbcexercises

import java.util.UUID

data class Order(
    val id: String = UUID.randomUUID().toString(),
    val orderNumber: Int,
    val quantity: Int,
    val subTotal: Int,
    val date: Long = System.currentTimeMillis(),
    var status: OrderStatus
)
