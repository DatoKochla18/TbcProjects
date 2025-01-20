package com.example.tbcexercises.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcexercises.R
import com.example.tbcexercises.data.model.Message
import com.example.tbcexercises.data.model.MessageType
import com.example.tbcexercises.databinding.ItemMessageFileBinding
import com.example.tbcexercises.databinding.ItemMessageTextBinding
import com.example.tbcexercises.databinding.ItemMessageVoiceBinding

class MessageAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffUtil) {

    companion object {
        private const val TEXT = 1
        private const val VOICE = 2
        private const val FILE = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TEXT -> MessageTextViewHolder(
                ItemMessageTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            VOICE -> MessageVoiceViewHolder(
                ItemMessageVoiceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> MessageFileViewHolder(
                ItemMessageFileBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MessageTextViewHolder -> holder.onBind()
            is MessageVoiceViewHolder -> holder.onBind()
            is MessageFileViewHolder -> holder.onBind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.messageType == MessageType.TEXT) TEXT else if (message.messageType == MessageType.VOICE) VOICE else FILE
    }

    inner class MessageTextViewHolder(private val binding: ItemMessageTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val message = getItem(adapterPosition)
            binding.txtMessage.text = message.lastMessage
            binding.txtUnreadMessage.text = message.unreadMessages.toString()
            binding.txtProfileName.text = message.owner
            binding.txtMessageDate.text = message.lastActive
            Glide.with(binding.imgProfilePhoto.context)
                .load(message.image)
                .placeholder(R.drawable.white_girl)
                .error(R.drawable.profile_photo)
                .into(binding.imgProfilePhoto)
        }
    }


    inner class MessageFileViewHolder(private val binding: ItemMessageFileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val message = getItem(adapterPosition)
            binding.txtUnreadMessage.text = message.unreadMessages.toString()
            binding.txtProfileName.text = message.owner
            binding.txtMessageDate.text = message.lastActive
            Glide.with(binding.imgProfilePhoto.context)
                .load(message.image)
                .placeholder(R.drawable.white_girl)
                .error(R.drawable.profile_photo)
                .into(binding.imgProfilePhoto)
        }
    }

    inner class MessageVoiceViewHolder(private val binding: ItemMessageVoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val message = getItem(adapterPosition)
            binding.txtUnreadMessage.text = message.unreadMessages.toString()
            binding.txtProfileName.text = message.owner
            binding.txtMessageDate.text = message.lastActive

            Glide.with(binding.imgProfilePhoto.context)
                .load(message.image)
                .placeholder(R.drawable.white_girl)
                .error(R.drawable.profile_photo)
                .into(binding.imgProfilePhoto)
        }
    }

}