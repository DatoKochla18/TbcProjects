package com.example.tbcexercises.data.model

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
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
    val messageType = if (lastMessageType == "text") {
        MessageType.TEXT
    } else if (lastMessageType == "voice") MessageType.VOICE else MessageType.FILE
}
