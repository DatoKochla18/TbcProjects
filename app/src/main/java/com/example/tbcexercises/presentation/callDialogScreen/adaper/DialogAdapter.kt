package com.example.tbcexercises.presentation.callDialogScreen.adaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.data.model.Dialog
import com.example.tbcexercises.databinding.ItemDialogBinding


class DialogAdapter(
    val onClick: (Dialog) -> Unit
) :
    ListAdapter<Dialog, DialogAdapter.DialogViewHolder>(DialogDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val binding = ItemDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DialogViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        holder.onBind()
    }

    inner class DialogViewHolder(private val binding: ItemDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val dialog = getItem(adapterPosition)
            binding.apply {
                txtDialogNumber.text = dialog.id
                txtDialogText.text = dialog.text

                root.setOnClickListener {
                    onClick(dialog)
                }
            }
        }

    }

}