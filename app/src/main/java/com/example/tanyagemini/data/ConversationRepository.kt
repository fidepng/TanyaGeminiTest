package com.example.tanyagemini.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ConversationRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val conversationsCollection = firestore.collection("conversations")

    suspend fun saveConversation(conversation: Conversation): String {
        val conversationId = conversation.id ?: UUID.randomUUID().toString()
        val conversationToSave = conversation.copy(id = conversationId)

        conversationsCollection
            .document(conversationId)
            .set(conversationToSave)
            .await()

        return conversationId
    }

    suspend fun getAllConversations(): List<Conversation> {
        return conversationsCollection
            .get()
            .await()
            .toObjects(Conversation::class.java)
    }

    suspend fun getConversationById(id: String): Conversation? {
        return conversationsCollection
            .document(id)
            .get()
            .await()
            .toObject(Conversation::class.java)
    }
}