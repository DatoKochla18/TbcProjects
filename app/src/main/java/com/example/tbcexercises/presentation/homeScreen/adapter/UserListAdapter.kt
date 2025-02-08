package com.example.tbcexercises.presentation.homeScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ItemUserBinding
import com.example.tbcexercises.domain.model.User

class UserListAdapter : PagingDataAdapter<User, UserListAdapter.UserListViewHolder>(UserDiffUtil) {


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
