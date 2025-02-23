package com.example.tbcexercises.presentation.home_screen.post_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.ItemPhohoTwoBinding
import com.example.tbcexercises.databinding.ItemPhotoOneBinding
import com.example.tbcexercises.databinding.ItemPhotoThreeBinding
import com.example.tbcexercises.databinding.ItemPostBinding
import com.example.tbcexercises.domain.model.Post
import com.example.tbcexercises.util.image_loader.ImageLoader
import javax.inject.Inject

class PostListAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : ListAdapter<Post, PostListAdapter.PostViewHolder>(PostDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onBind()
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val post = getItem(adapterPosition)
            val photos = post.images ?: emptyList()

            binding.apply {
                txtPostMessage.text = post.title
                txtPostDate.text = post.owner.postDate
                txtUserName.text = post.owner.fullName
                txtLikeCount.text = post.likes
                txtMessageCount.text = post.comments

                imageLoader.loadImage(imgProfile, post.owner.profile)
            }

            val container = binding.flPhotosContainer

            container.removeAllViews()

            when (photos.size) {
                1 -> {
                    val imageOneBinding = ItemPhotoOneBinding.inflate(
                        LayoutInflater.from(container.context), container, false
                    )
                    container.addView(imageOneBinding.root)
                    imageLoader.loadImage(imageOneBinding.imgPhotoMain, photos[0])
                }

                2 -> {
                    val imageTwoBinding = ItemPhohoTwoBinding.inflate(
                        LayoutInflater.from(container.context), container, false
                    )
                    container.addView(imageTwoBinding.root)
                    imageLoader.loadImage(imageTwoBinding.imgPhotoLeft, photos[0])
                    imageLoader.loadImage(imageTwoBinding.imgPhotoLeft, photos[1])
                }

                3 -> {
                    val imageThreeBinding = ItemPhotoThreeBinding.inflate(
                        LayoutInflater.from(container.context), container, false
                    )
                    container.addView(imageThreeBinding.root)
                    imageLoader.loadImage(imageThreeBinding.imgPhotoLeft, photos[0])
                    imageLoader.loadImage(imageThreeBinding.imgPhotoTopRight, photos[1])
                    imageLoader.loadImage(imageThreeBinding.imgPhotoBottomRight, photos[2])
                }

                else -> {
                    container.visibility = View.GONE
                    binding.separator1.isVisible = false
                }
            }
        }
    }
}

