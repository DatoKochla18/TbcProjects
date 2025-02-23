package com.example.tbcexercises.presentation.home_screen.story_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.ItemStoryBinding
import com.example.tbcexercises.domain.model.Story
import com.example.tbcexercises.util.image_loader.ImageLoader
import com.example.tbcexercises.util.image_loader.ImageLoaderImpl
import javax.inject.Inject

class StoryListAdapter @Inject constructor(
    private val imageLoader:ImageLoader
) : ListAdapter<Story, StoryListAdapter.StoryViewHolder>(StoryDiffUtil) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.onBind()
    }


    inner class StoryViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val story = getItem(adapterPosition)

            binding.apply {
                txtTitle.text = story.title
                imageLoader.loadImage(imgStoryPhoto, story.cover)
            }
        }
    }

}