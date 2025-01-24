package com.example.tbcexercises.presentation.homeScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcexercises.R
import com.example.tbcexercises.data.model.User
import com.example.tbcexercises.databinding.ItemUserBinding

class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind()
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val user = getItem(adapterPosition)

            Glide.with(binding.imgUser.context)
                .load(user.avatar)
                .placeholder(R.drawable.person)
                .error(R.drawable.person)
                .into(binding.imgUser)

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
