package com.example.tbcexercises.data.imageLoader

import android.widget.ImageView
import coil.load
import coil.size.Scale
import com.example.tbcexercises.R
import com.example.tbcexercises.domain.imageLoader.ImageLoader

object CoilImageLoader : GlideImageLoader() {
    override fun loadImage(url: String, imageView: ImageView) {
        imageView.load(url) {
            placeholder(R.drawable.downloading)
            error(R.drawable.error)
            scale(Scale.FIT)
        }
    }
}