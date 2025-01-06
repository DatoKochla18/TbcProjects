package com.example.tbcexercises.GameScreen

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.Models.ButtonData

object ButtonDiffUtil  : DiffUtil.ItemCallback<ButtonData>() {
    override fun areItemsTheSame(oldItem: ButtonData, newItem: ButtonData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ButtonData, newItem: ButtonData): Boolean {
        return oldItem == newItem
    }
}