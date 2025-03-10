package com.example.tbcexercises.presentation.home_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.ItemUserBinding
import com.example.tbcexercises.domain.model.Profile
import com.example.tbcexercises.presentation.extension.loadImg

class UserListAdapter() :
    PagingDataAdapter<Profile, UserListAdapter.UserListViewHolder>(UserDiffUtil) {


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
            binding.imgUser.loadImg(user.avatar)


            binding.apply {
                txtEmail.text = user.email
                txtFullName.text = user.fullName
            }
        }
    }
}
