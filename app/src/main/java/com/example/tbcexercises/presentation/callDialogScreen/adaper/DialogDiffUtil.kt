package com.example.tbcexercises.presentation.callDialogScreen.adaper

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.data.model.Dialog

object DialogDiffUtil : DiffUtil.ItemCallback<Dialog>() {
    override fun areItemsTheSame(oldItem: Dialog, newItem: Dialog): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Dialog, newItem: Dialog): Boolean {
        return oldItem == newItem
    }
}