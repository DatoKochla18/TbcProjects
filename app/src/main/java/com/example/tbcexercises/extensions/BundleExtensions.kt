package com.example.tbcexercises.extensions

import android.os.Build
import android.os.Bundle
import com.example.tbcexercises.model.Order
import com.example.tbcexercises.model.OrderStatus

fun Bundle.getOrderStatus(key: String): OrderStatus? {
    return if (Build.VERSION.SDK_INT < 33) {
        getParcelable(key)
    } else {
        getParcelable(key, OrderStatus::class.java)

    }
}

fun Bundle.getOrder(key: String): Order? {
    return if (Build.VERSION.SDK_INT < 33) {
        getParcelable(key)
    } else {
        getParcelable(key, Order::class.java)

    }
}