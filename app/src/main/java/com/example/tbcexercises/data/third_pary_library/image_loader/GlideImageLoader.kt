package com.example.tbcexercises.data.third_pary_library.image_loader

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.tbcexercises.domain.third_party_library.image_loader.ImageLoader
import javax.inject.Inject

class GlideImageLoader @Inject constructor() : ImageLoader {
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