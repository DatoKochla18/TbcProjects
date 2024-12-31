package com.example.tbcexercises.MessageChatScreen

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.Models.Message

object MessageDiffUtil : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}