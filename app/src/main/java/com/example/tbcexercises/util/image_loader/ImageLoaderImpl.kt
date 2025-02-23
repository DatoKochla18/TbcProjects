package com.example.tbcexercises.util.image_loader

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageLoaderImpl : ImageLoader {

    override fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}