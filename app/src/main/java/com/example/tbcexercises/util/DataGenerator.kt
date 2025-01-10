package com.example.tbcexercises.util

import com.example.tbcexercises.R
import com.example.tbcexercises.model.Order
import com.example.tbcexercises.model.OrderColor
import com.example.tbcexercises.model.OrderStatus

fun generateData(): List<Order> {
    return listOf(
        Order(
            img = R.drawable.brown_chair,
            name = "Brown Chair",
            orderColor = OrderColor.BROWN,
            orderStatus = OrderStatus.ACTIVE,
            price = 120.00f,
            quantity = 3
        ),

        Order(
            img = R.drawable.chair_yellow,
            name = "Yellow Chair",
            orderColor = OrderColor.BROWN,
            orderStatus = OrderStatus.COMPLETED,
            price = 520.00f,
            quantity = 3
        ),

        Order(
            img = R.drawable.chair_white,
            name = "Yellow Chair",
            orderColor = OrderColor.WHITE,
            orderStatus = OrderStatus.COMPLETED,
            price = 1120.00f,
            quantity = 1
        )

    )
}