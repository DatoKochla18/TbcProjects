package com.example.tbcexercises.presentation.locationScreen.viewpager

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.domain.model.Location

object LocationDiffUtil : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }
}