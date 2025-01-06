package com.example.tbcexercises

import androidx.recyclerview.widget.DiffUtil

object ButtonDiffUtil  : DiffUtil.ItemCallback<ButtonData>() {
    override fun areItemsTheSame(oldItem: ButtonData, newItem: ButtonData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ButtonData, newItem: ButtonData): Boolean {
        return oldItem == newItem
    }
}