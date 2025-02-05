package com.example.tbcexercises.presentation.entryScreen.passwordAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.R
import com.example.tbcexercises.data.model.Password
import com.example.tbcexercises.databinding.ItemPasswordBinding

class PasswordListAdapter() :
    ListAdapter<Password, PasswordListAdapter.PasswordViewHolder>(PasswordDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val binding =
            ItemPasswordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PasswordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        holder.onBind()
    }

    inner class PasswordViewHolder(private val binding: ItemPasswordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val password = getItem(adapterPosition)

            val drawable = ContextCompat.getDrawable(binding.root.context, R.drawable.rounded_background)

            val color = if (password.enteredNumber == null) {
                ContextCompat.getColor(binding.root.context, R.color.gray)
            } else {
                ContextCompat.getColor(binding.root.context, R.color.green)
            }
            drawable?.let {
                DrawableCompat.setTint(it, color)
                binding.password.background = it
            }

        }
    }
}