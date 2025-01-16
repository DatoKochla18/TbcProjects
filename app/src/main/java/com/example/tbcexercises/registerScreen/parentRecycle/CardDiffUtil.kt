package com.example.tbcexercises.registerScreen.parentRecycle

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.models.Card

object CardDiffUtil : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }
}
