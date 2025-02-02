package com.example.tbcexercises.presentation.callDialogScreen.adaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.data.model.Contact
import com.example.tbcexercises.databinding.ItemDialogResultBinding
import com.example.tbcexercises.common.ContactDiffUtil


class DialogResultAdapter(val onClick: (Contact) -> Unit) :
    ListAdapter<Contact, DialogResultAdapter.DialogResultViewHolder>(
        ContactDiffUtil
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogResultViewHolder {
        val binding =
            ItemDialogResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DialogResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DialogResultViewHolder, position: Int) {
        holder.onBind()
    }

    inner class DialogResultViewHolder(private val binding: ItemDialogResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val contact = getItem(adapterPosition)
            binding.apply {
                txtName.text = contact.name
                txtPhoneNumber.text = contact.phoneNumber
                root.setOnClickListener {
                    onClick(contact)
                }
            }
        }
    }
}