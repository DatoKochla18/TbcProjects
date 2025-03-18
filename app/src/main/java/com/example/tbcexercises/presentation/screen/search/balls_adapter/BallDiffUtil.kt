package com.example.tbcexercises.presentation.screen.search.balls_adapter

import androidx.recyclerview.widget.DiffUtil

object BallDiffUtil : DiffUtil.ItemCallback<Ball>() {
    override fun areItemsTheSame(oldItem: Ball, newItem: Ball): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Ball, newItem: Ball): Boolean {
        return oldItem == newItem
    }
}