package com.example.tbcexercises.domain.imageLoader

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(url: String, imageView: ImageView)
}
