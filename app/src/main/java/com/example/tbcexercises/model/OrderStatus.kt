package com.example.tbcexercises.model

enum class OrderStatus(val names: String, val buttonText: String) {
    ACTIVE("In Delivery", "Track Order"),
    COMPLETED("Completed", "Buy Again")
}