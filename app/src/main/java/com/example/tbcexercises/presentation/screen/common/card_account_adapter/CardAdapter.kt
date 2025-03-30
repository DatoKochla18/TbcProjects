package com.example.tbcexercises.presentation.screen.common.card_account_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ItemCardBinding
import com.example.tbcexercises.presentation.extension.loadImg
import com.example.tbcexercises.presentation.model.Card

class CardAdapter(val onClick: (Card) -> Unit) :
    ListAdapter<Card, CardAdapter.CardViewHolder>(CardDiffUtil) {

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
            val card = getItem(adapterPosition)
            binding.root.setOnClickListener {
                onClick(card)

            }

            binding.txtCardNumber.text = card.accountNumber
            binding.txtPrice.text = card.balance.toString()
            binding.txtMoneyType.text = binding.root.context.getString(R.string.cash)
            binding.imgCardLogo.loadImg(card.cardLogo)
        }
    }

}