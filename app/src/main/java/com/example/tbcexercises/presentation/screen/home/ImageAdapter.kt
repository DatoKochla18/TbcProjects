package com.example.tbcexercises.presentation.screen.home

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.databinding.ViewholderImageBinding
import com.example.tbcexercises.presentation.base.BaseAdapter
import com.example.tbcexercises.presentation.extension.loadImg
import com.example.tbcexercises.presentation.model.Image

class ImageAdapter(val onClick: (String) -> Unit) :
    BaseAdapter<Image, ViewholderImageBinding>(object :
        DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(
            oldItem: Image,
            newItem: Image,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Image,
            newItem: Image,
        ): Boolean {
            return oldItem == newItem
        }
    }, bindingInflater = ViewholderImageBinding::inflate) {
    override fun bind(binding: ViewholderImageBinding, item: Image) {
        binding.apply {
            txtImageAuthor.text = item.userName
            txtImageTitle.text = item.title

            imgBackground.loadImg(item.badgeUrl)
            root.setOnClickListener {
                onClick(item.hex)
            }
        }
    }
}