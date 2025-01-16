package com.example.tbcexercises.registerScreen.childRecycle

import androidx.recyclerview.widget.DiffUtil
import com.example.tbcexercises.models.Views

object ViewDiffUtil : DiffUtil.ItemCallback<Views>() {
    override fun areItemsTheSame(oldItem: Views, newItem: Views): Boolean {
        return oldItem.fieldId == newItem.fieldId
    }

    override fun areContentsTheSame(oldItem: Views, newItem: Views): Boolean {
        return oldItem == newItem
    }

}