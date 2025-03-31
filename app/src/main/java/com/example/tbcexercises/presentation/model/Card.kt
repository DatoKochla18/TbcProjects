package com.example.tbcexercises.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class Card(
    val id: Int,
    val accountName: String,
    val accountNumber: String,
    val valueType: String,
    val valueTypeFormatted: String,
    val balance: Int,
    val cardLogo: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(accountName)
        parcel.writeString(accountNumber)
        parcel.writeString(valueType)
        parcel.writeInt(balance)
        parcel.writeString(cardLogo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Card> {
        override fun createFromParcel(parcel: Parcel): Card {
            return Card(parcel)
        }

        override fun newArray(size: Int): Array<Card?> {
            return arrayOfNulls(size)
        }
    }
}