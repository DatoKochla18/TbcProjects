package com.example.tbcexercises.presentation.screen.common.card_account_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.presentation.model.Card

object CardDiffUtil : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }
}