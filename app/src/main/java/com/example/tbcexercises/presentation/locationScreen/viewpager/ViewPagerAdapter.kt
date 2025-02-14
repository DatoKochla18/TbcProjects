package com.example.tbcexercises.presentation.locationScreen.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ItemLocationBinding
import com.example.tbcexercises.domain.model.Location

class ViewPagerAdapter :
    ListAdapter<Location, ViewPagerAdapter.ViewPagerViewHolder>(LocationDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.onBind()
    }


    inner class ViewPagerViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {


            val location = getItem(adapterPosition)

            Glide.with(binding.imgLocation.context)
                .load(location.cover)
                .placeholder(R.drawable.cancel)
                .error(R.drawable.cancel)
                .into(binding.imgLocation)


            binding.apply {
                txtPrice.text = location.price
                txtLocationName.text = location.location
                txtReactionCount.text = location.reactionCount.toString()
                txtLocationDetail.text = location.title
            }

            binding.rbRatingBar.rating = location.rate?.toFloat() ?: 0f
        }
    }
}