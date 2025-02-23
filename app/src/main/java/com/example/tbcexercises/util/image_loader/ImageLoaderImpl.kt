package com.example.tbcexercises.util.image_loader

import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject


class ImageLoaderImpl @Inject constructor() : ImageLoader {

    override fun loadImage(
        imageView: ImageView,
        url: String?,
        placeholder: Int,
        error: Int
    ) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(placeholder)
            .error(error)
            .into(imageView)
    }
}