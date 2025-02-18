package com.example.tbcexercises.domain.third_party_library.image_loader

import android.widget.ImageView
import com.example.tbcexercises.R

interface ImageLoader {
    fun loadImage(
        imageView: ImageView,
        url: String?,
        placeholder: Int = R.drawable.person,
        error: Int = R.drawable.person
    )
}