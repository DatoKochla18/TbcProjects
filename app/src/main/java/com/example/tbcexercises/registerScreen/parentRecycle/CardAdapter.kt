package com.example.tbcexercises.registerScreen.parentRecycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.registerScreen.childRecycle.ViewsAdapter
import com.example.tbcexercises.databinding.ItemCardViewBinding
import com.example.tbcexercises.models.Card

class CardAdapter(val onTextChanged: (Int, String?) -> Unit) :
    ListAdapter<Card, CardAdapter.CardViewHolder>(CardDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding =
            ItemCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.onBind()
    }

    inner class CardViewHolder(private val binding: ItemCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val viewAdapter = ViewsAdapter(onChangedText = { id, text -> onTextChanged(id, text) })
            val card = getItem(adapterPosition)

            binding.rvFields.apply {
                layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
                adapter = viewAdapter
            }

            viewAdapter.submitList(card.views)

        }
    }

}