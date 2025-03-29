package com.example.tbcexercises.presentation.extension

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.example.tbcexercises.R
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    message: String,
    @ColorRes backgroundColor: Int = R.color.black,
    @ColorRes textColor: Int = R.color.white,
) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    val bgColorValue = ContextCompat.getColor(context, backgroundColor)
    val textColorValue = ContextCompat.getColor(context, textColor)
    snackBar.view.apply {
        setBackgroundColor(bgColorValue)
        backgroundTintList = ColorStateList.valueOf(bgColorValue)

        findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.let { textView ->
            textView.setTextColor(textColorValue)
            textView.setBackgroundColor(bgColorValue)
        }
    }

    snackBar.show()
}