package com.example.tanyagemini

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.Bitmap
import com.example.tanyagemini.data.ChatData
import com.example.tanyagemini.data.Chats
import com.example.tanyagemini.data.Conversation
import com.example.tanyagemini.data.ConversationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _chatState = MutableStateFlow(ChatState())
    val chatState = _chatState.asStateFlow()

    private val conversationRepository = ConversationRepository()
    private var currentConversationId: String? = null

    fun onEvent(event: ChatUiEvent) {
        when (event) {
            is ChatUiEvent.SendPrompt -> {
                if (event.prompt.isNotEmpty() || event.bitmap != null) {
                    addPrompt(event.prompt, event.bitmap)
                    saveCurrentConversation()

                    if (event.bitmap != null) {
                        getResponseWithImage(event.prompt, event.bitmap)
                    } else {
                        getResponse(event.prompt)
                    }

                    // Reset bitmap after sending
                    _chatState.update { it.copy(bitmap = null) }
                }
            }

            is ChatUiEvent.UpdatePrompt -> {
                _chatState.update {
                    it.copy(prompt = event.newPrompt)
                }
            }
        }
    }

    // Add a method to fetch conversations
    suspend fun fetchConversations(): List<Conversation> {
        return try {
            conversationRepository.getAllConversations()
        } catch (e: Exception) {
            // Log the error or handle it as needed
            emptyList()
        }
    }


    private fun saveCurrentConversation() {
        viewModelScope.launch {
            val currentMessages = chatState.value.chatList

            if (currentMessages.isNotEmpty()) {
                val conversation = Conversation(
                    id = currentConversationId,
                    messages = currentMessages,
                    title = currentMessages.first().prompt.take(20)
                )

                currentConversationId = conversationRepository.saveConversation(conversation)
            }
        }
    }

//    fun clearChat() {
//        _chatState.update {
//            it.copy(chatList = emptyList(), prompt = "")
//        }
//    }


    private fun addPrompt(prompt: String, bitmap: Bitmap?) {
        _chatState.update {
            it.copy(
                chatList = it.chatList.toMutableList().apply {
                    add(0, Chats(prompt, bitmap, true))
                },
                prompt = "",
                bitmap = null
            )
        }
    }

    private fun getResponse(prompt: String) {
        viewModelScope.launch {
            val chat = ChatData.getResponse(prompt)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    }
                )
            }
        }
    }

    private fun getResponseWithImage(prompt: String, bitmap: Bitmap) {
        viewModelScope.launch {
            val chat = ChatData.getResponseWithImage(prompt, bitmap)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    }
                )
            }
        }
    }
}






