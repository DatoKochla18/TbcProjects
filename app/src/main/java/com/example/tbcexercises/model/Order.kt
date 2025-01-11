package com.example.tbcexercises.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Order(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val img: Int,
    val quantity: Int,
    val price: Float,
    val orderStatus: OrderStatus,
    val orderColor: OrderColor,
    var ratingStars: Int? = null,
    var ratingText: String? = null
) : Parcelable
