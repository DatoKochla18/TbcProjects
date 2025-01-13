package com.example.tbcexercises.paymentScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.Model.Card
import com.example.tbcexercises.databinding.ItemCardBinding
import com.example.tbcexercises.extensions.toCardNumber

class CardViewPagerAdapter(val onLongClick: (Card) -> Boolean) :
    ListAdapter<Card, CardViewPagerAdapter.CardViewHolder>(CardDiffUtil) {


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

            binding.apply {
                txtCardHolderNameDisplay.text = card.cardHolderName
                txtCardNumberDisplay.text = card.cardNumber.toCardNumber()
                txtCardDateDisplay.text = card.expiredDate

                imgCard.setImageResource(card.cardType.img)

                root.setOnLongClickListener {
                    onLongClick(card)
                }
            }
        }
    }
}