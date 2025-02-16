package com.example.tbcexercises.data.imageLoader

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.tbcexercises.R
import com.example.tbcexercises.domain.imageLoader.ImageLoader
import javax.inject.Inject

open class GlideImageLoader : ImageLoader {
    override fun loadImage(url: String, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.downloading)
            .error(R.drawable.error)
            .into(imageView)
    }
}