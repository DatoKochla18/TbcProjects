package com.example.tbcexercises.presentation.home_screen.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.Profile

object UserDiffUtil : DiffUtil.ItemCallback<Profile>() {
    override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean {
        return oldItem == newItem
    }
}