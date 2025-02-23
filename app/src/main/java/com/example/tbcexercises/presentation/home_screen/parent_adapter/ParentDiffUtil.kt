package com.example.tbcexercises.presentation.home_screen.parent_adapter

import androidx.recyclerview.widget.DiffUtil


object ParentDiffUtil : DiffUtil.ItemCallback<ParentItem>() {
    override fun areItemsTheSame(oldItem: ParentItem, newItem: ParentItem): Boolean {
        return when {
            oldItem is ParentItem.PostItem && newItem is ParentItem.PostItem -> oldItem.data == newItem.data
            oldItem is ParentItem.StoryItem && newItem is ParentItem.StoryItem -> oldItem.data == newItem.data
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: ParentItem, newItem: ParentItem): Boolean {
        return oldItem == newItem
    }
}