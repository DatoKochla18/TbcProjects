package com.example.tbcexercises.presentation.entryScreen.passwordAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.data.model.Password

object PasswordDiffUtil : DiffUtil.ItemCallback<Password>() {
    override fun areItemsTheSame(oldItem: Password, newItem: Password): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Password, newItem: Password): Boolean {
        return oldItem == newItem
    }
}