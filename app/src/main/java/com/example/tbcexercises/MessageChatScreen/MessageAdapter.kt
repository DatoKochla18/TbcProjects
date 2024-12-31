package com.example.tbcexercises.MessageChatScreen

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.Extensions.toDate
import com.example.tbcexercises.Models.Message
import com.example.tbcexercises.databinding.ItemMessageLeftBinding
import com.example.tbcexercises.databinding.ItemMessageRightBinding

class MessageAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffUtil) {

    companion object {
        private const val MESSAGE_LEFT = 1
        private const val MESSAGE_RIGHT = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MESSAGE_LEFT -> MessageLeftViewHolder(
                ItemMessageLeftBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            else -> MessageRightViewHolder(
                ItemMessageRightBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position % 2 == 0)) MESSAGE_LEFT else MESSAGE_RIGHT
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MessageLeftViewHolder -> holder.onBind()
            is MessageRightViewHolder -> holder.onBind()
        }
    }

    inner class MessageLeftViewHolder(private val binding: ItemMessageLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val message = getItem(adapterPosition)
            binding.apply {
                txtMessage.text = message.text
                txtDate.text = message.dateTime.toDate()
            }
            Log.d("tag ${binding.txtMessage.text}", "position : ${adapterPosition}")
        }
    }

    inner class MessageRightViewHolder(private val binding: ItemMessageRightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val message = getItem(adapterPosition)
            binding.apply {
                txtMessage.text = message.text
                txtDate.text = message.dateTime.toDate()
            }

            Log.d("tag ${binding.txtMessage.text}", "position : ${adapterPosition}")
        }
    }
}