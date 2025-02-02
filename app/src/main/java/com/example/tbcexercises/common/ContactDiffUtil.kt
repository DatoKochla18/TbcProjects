package com.example.tbcexercises.common

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.data.model.Contact

object ContactDiffUtil : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}