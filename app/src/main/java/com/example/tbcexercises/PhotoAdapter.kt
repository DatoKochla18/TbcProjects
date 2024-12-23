package com.example.tbcexercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.PhotoCardBinding

class PhotoAdapter(var photos: List<Photo>) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = PhotoCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return photos.size
    }


    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        return holder.onBind()
    }


    inner class PhotoViewHolder(private val binding: PhotoCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind() {
            val photoCard = photos[adapterPosition]
            binding.apply {
                imPhoto.setImageResource(photoCard.image)
                txtPrice.text = "$${photoCard.price}"
                txtTitle.text = photoCard.title
            }
        }
    }

}