package com.example.tbcexercises.presentation.home_screen.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.User

object UserDiffUtil : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}