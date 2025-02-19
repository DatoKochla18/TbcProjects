package com.example.tbcexercises.presentation.home_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ItemUserBinding
import com.example.tbcexercises.domain.model.User
import com.example.tbcexercises.domain.third_party_library.image_loader.ImageLoader
import javax.inject.Inject

class UserListAdapter @Inject constructor(private val imageLoader: ImageLoader) :
    PagingDataAdapter<User, UserListAdapter.UserListViewHolder>(UserDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.onBind()
    }

    inner class UserListViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            //val user = getItem(adapterPosition)!! deprecated
            val user = getItem(bindingAdapterPosition)!!
            imageLoader.loadImage(
                binding.imgUser, user.avatar
            )


            binding.apply {
                txtEmail.text = user.email
                txtFullName.text = root.context.resources.getString(
                    R.string.full_name,
                    user.firstName,
                    user.lastName
                )
            }
        }
    }
}
