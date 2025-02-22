package com.example.tbcexercises.util.image_loader

import android.widget.ImageView

interface ImageLoader {

     fun loadImage(
         imageView: ImageView,
         url: String?,
    )
}