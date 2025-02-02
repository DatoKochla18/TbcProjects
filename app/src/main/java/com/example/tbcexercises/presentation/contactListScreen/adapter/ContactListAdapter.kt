package com.example.tbcexercises.presentation.contactListScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.data.model.Contact
import com.example.tbcexercises.databinding.ItemContactListBinding
import com.example.tbcexercises.common.ContactDiffUtil

class ContactListAdapter(val onEdit: (Int) -> Unit) :
    ListAdapter<Contact, ContactListAdapter.ContactListViewHolder>(ContactDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        val binding =
            ItemContactListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        holder.onBind()
    }


    inner class ContactListViewHolder(private val binding: ItemContactListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            binding.apply {
                val contact = getItem(adapterPosition)
                txtname.text = contact.name
                txtphoneNumber.text = contact.phoneNumber

                imbEdit.setOnClickListener {
                    onEdit(contact.id)
                }

            }
        }
    }
}