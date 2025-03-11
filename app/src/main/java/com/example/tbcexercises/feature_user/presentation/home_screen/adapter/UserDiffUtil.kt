package com.example.tbcexercises.feature_user.presentation.home_screen.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.core.domain.model.Profile
import com.example.tbcexercises.feature_user.domain.model.User

object UserDiffUtil : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}