package com.example.tbcexercises.presentation.screen.bottom_sheet_from_account.card_account_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.ItemCardBinding
import com.example.tbcexercises.presentation.model.Card

class CardAdapter : ListAdapter<Card, CardAdapter.CardViewHolder>(CardDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.onBind()
    }


    inner class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {

        }
    }

}