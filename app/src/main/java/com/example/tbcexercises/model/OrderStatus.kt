package com.example.tbcexercises.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class OrderStatus(val names: String, val buttonText: String) : Parcelable {
    ACTIVE("In Delivery", "Track Order"),
    COMPLETED("Completed", "Buy Again")
}