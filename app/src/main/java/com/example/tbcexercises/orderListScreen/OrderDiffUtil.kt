package com.example.tbcexercises.orderListScreen

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.model.Order

object OrderDiffUtil : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}