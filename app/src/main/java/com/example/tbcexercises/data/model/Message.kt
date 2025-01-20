package com.example.tbcexercises.data.model

import com.squareup.moshi.Json

data class Message(
    val id: Int,
    val image: String?,
    val owner: String,
    @Json(name = "last_message") val lastMessage: String,
    @Json(name = "last_active") val lastActive: String,
    @Json(name = "unread_messages") val unreadMessages: Int,
    @Json(name = "is_typing") val isTyping: Boolean,
    @Json(name = "laste_message_type") val lastMessageType: String,
) {
    val messageType = when (lastMessageType) {
        "text" -> {
            MessageType.TEXT
        }
        "voice" -> MessageType.VOICE
        else -> MessageType.FILE
    }
}
