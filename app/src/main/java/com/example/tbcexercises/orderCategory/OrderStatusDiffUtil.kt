package com.example.tbcexercises.orderCategory

import androidx.recyclerview.widget.DiffUtil

val orderStatusDiffUtil = object : DiffUtil.ItemCallback<OrderStatus>() {
    override fun areItemsTheSame(oldItem: OrderStatus, newItem: OrderStatus): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrderStatus, newItem: OrderStatus): Boolean {
        return oldItem == newItem
    }

}