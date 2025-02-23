package com.example.tbcexercises.presentation.home_screen.parent_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.ItemParentBinding
import com.example.tbcexercises.presentation.home_screen.post_adapter.PostListAdapter
import com.example.tbcexercises.presentation.home_screen.story_adapter.StoryListAdapter
import com.example.tbcexercises.util.image_loader.ImageLoader
import javax.inject.Inject

class ParentListAdapter @Inject constructor(private val imageLoader: ImageLoader) :
    ListAdapter<ParentItem, RecyclerView.ViewHolder>(ParentDiffUtil) {

    companion object {
        private const val POST = 1
        private const val STORY = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            POST -> PostViewHolder(
                ItemParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> StoryViewHolder(
                ItemParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> holder.onBind()
            is StoryViewHolder -> holder.onBind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ParentItem.PostItem -> POST
            is ParentItem.StoryItem -> STORY
        }
    }

    inner class PostViewHolder(private val binding: ItemParentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val item = getItem(adapterPosition) as ParentItem.PostItem
            val postAdapter = PostListAdapter(imageLoader)

            binding.rvChild.apply {
                adapter = postAdapter
                layoutManager = LinearLayoutManager(binding.root.context)
            }

            postAdapter.submitList(item.data)
        }
    }

    inner class StoryViewHolder(private val binding: ItemParentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val item = getItem(adapterPosition) as ParentItem.StoryItem

            val storiesAdapter = StoryListAdapter(imageLoader)

            binding.rvChild.apply {
                adapter = storiesAdapter
                layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

            }
            storiesAdapter.submitList(item.data)
        }

    }

}
