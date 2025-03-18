package com.example.tbcexercises.presentation.screen.search.category_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.presentation.model.Category

object CategoryDiffUtil : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}