package com.example.tbcexercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.ItemGameCardBinding

class GameAdapter (val onClick: (ButtonData) -> Unit) : ListAdapter<ButtonData, GameAdapter.GameViewHolder>(ButtonDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding =
            ItemGameCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.onBind()
    }
    inner class GameViewHolder(private val binding: ItemGameCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val card = getItem(adapterPosition)

            binding.btnCard.setImageResource(card.img)

            binding.btnCard.setOnClickListener {
                onClick(card)
            }

        }
    }
}