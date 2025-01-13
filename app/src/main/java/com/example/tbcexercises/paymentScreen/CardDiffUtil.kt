package com.example.tbcexercises.paymentScreen

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.Model.Card

object CardDiffUtil : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }
}