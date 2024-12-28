package com.example.tbcexercises.order

import android.os.Parcel
import android.os.Parcelable
import com.example.tbcexercises.Extensions.format
import com.example.tbcexercises.orderCategory.OrderStatus
import java.util.UUID

data class Order(
    val id: String = UUID.randomUUID().toString().format().substring(0, 10),
    val orderNumber: Int,
    val quantity: Int,
    val subTotal: Int,
    val date: Long = System.currentTimeMillis(),
    var status: OrderStatus,
    val address: String = "Manchester"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readParcelable(OrderStatus::class.java.classLoader)!!,
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(orderNumber)
        parcel.writeInt(quantity)
        parcel.writeInt(subTotal)
        parcel.writeLong(date)
        parcel.writeParcelable(status, flags)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}
