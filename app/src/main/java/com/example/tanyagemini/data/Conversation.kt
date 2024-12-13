package com.example.tanyagemini.data

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Conversation(
    val id: String? = null,
    val messages: List<Chats> = emptyList(),
    @ServerTimestamp
    val createdAt: Date? = null,
    val title: String = "New Conversation"
)