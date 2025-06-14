package com.example.tbcexercises.presentation.screen.home_screen.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.databinding.ViewholderBreedBinding
import com.example.tbcexercises.presentation.base.BaseAdapter
import com.example.tbcexercises.presentation.extension.loadImg
import com.example.tbcexercises.presentation.model.Breed

class HomeAdapter(val onClick: (String) -> Unit) :
    BaseAdapter<Breed, ViewholderBreedBinding>(object :
        DiffUtil.ItemCallback<Breed>() {
        override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem == newItem
        }

    }, bindingInflater = ViewholderBreedBinding::inflate) {
    override fun bind(binding: ViewholderBreedBinding, item: Breed) {
        binding.root.setOnClickListener {
            onClick(item.name)
        }

        binding.apply {
            imgBreed.loadImg(item.imageUrl)

            txtBreed.text = item.name
            txtBreedOrigin.text = item.breedOrigin
        }
    }
}