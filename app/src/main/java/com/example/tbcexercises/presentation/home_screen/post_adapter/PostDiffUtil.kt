package com.example.tbcexercises.presentation.home_screen.post_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.Post

object PostDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}