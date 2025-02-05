package com.example.tbcexercises.presentation.entryScreen.dialogAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.data.model.Dialog
import com.example.tbcexercises.data.model.DialogType
import com.example.tbcexercises.databinding.ItemDialogImageBinding
import com.example.tbcexercises.databinding.ItemDialogNumberBinding

class DialogAdapter(
    private val dialogs: List<Dialog>,
    val onDialogNumberClick: (Dialog) -> Unit,
    val onDialogBackSpaceClick: (Dialog) -> Unit,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val NUMBER_VIEW_TYPE = 1
        private const val IMAGE_VIEW_TYPE = 2
    }

    override fun getItemViewType(position: Int): Int {
        val dialog = dialogs[position]
        return when (dialog.dialogType) {
            DialogType.NUMBER -> NUMBER_VIEW_TYPE
            else -> IMAGE_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NUMBER_VIEW_TYPE -> DialogNumberViewHolder(
                ItemDialogNumberBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> DialogImageViewHolder(
                ItemDialogImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int = dialogs.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DialogNumberViewHolder -> holder.onBind()
            is DialogImageViewHolder -> holder.onBind()
        }
    }

    inner class DialogNumberViewHolder(private val binding: ItemDialogNumberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val dialog = dialogs[adapterPosition]

            binding.apply {
                txtNumber.text = dialog.textOnView.toString()
                root.setOnClickListener {
                    onDialogNumberClick(dialog)
                }
            }
        }
    }

    inner class DialogImageViewHolder(private val binding: ItemDialogImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val dialog = dialogs[adapterPosition]

            binding.imgPhoto.setImageResource(dialog.textOnView)
            if (dialog.dialogType == DialogType.BACKSPACE) {
                binding.root.setOnClickListener { onDialogBackSpaceClick(dialog) }
            }
        }
    }

}