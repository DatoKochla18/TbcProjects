package com.example.tbcexercises.presentation.productListScreen.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.Product

object ProductDiffUtil : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}