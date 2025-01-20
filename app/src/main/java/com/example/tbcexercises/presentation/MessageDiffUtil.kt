package com.example.tbcexercises.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.data.model.Message

object MessageDiffUtil : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}