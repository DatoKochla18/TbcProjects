package com.example.tbcexercises.presentation.extension

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import com.example.tbcexercises.R

fun ImageView.loadImg(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.person)
        .error(R.drawable.person)
        .into(this)
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    )
}