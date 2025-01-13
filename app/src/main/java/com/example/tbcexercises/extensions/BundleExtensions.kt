package com.example.tbcexercises.extensions

import android.os.Build
import android.os.Bundle
import com.example.tbcexercises.model.Card

fun Bundle.getCard(key: String): Card? {
    return if (Build.VERSION.SDK_INT < 33) {
        getParcelable(key)
    } else {
        getParcelable(key, Card::class.java)

    }
}
