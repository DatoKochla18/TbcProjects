package com.example.tbcexercises.presentation.home_screen.story_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.Story

object StoryDiffUtil : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem == newItem
    }
}