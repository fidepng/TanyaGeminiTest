package com.example.tanyagemini

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.Bitmap
import com.example.tanyagemini.data.ChatData
import com.example.tanyagemini.data.Chats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatState(
    val chatList: List<Chats> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)
class ChatViewModel : ViewModel() {

    private val _chatState = MutableStateFlow(ChatState())
    val chatState = _chatState.asStateFlow()

    fun onEvent(event: ChatUiEvent) {
        when (event) {
            is ChatUiEvent.SendPrompt -> {
                if (event.prompt.isNotEmpty() || event.bitmap != null) {
                    addPrompt(event.prompt, event.bitmap)

                    if (event.bitmap != null) {
                        getResponseWithImage(event.prompt, event.bitmap)
                    } else {
                        getResponse(event.prompt)
                    }
                }
            }

            is ChatUiEvent.UpdatePrompt -> {
                _chatState.update {
                    it.copy(prompt = event.newPrompt)
                }
            }
        }
    }

    private fun updateChatList(newChat: Chats) {
        _chatState.update { it.copy(chatList = listOf(newChat) + it.chatList) }
    }

    private fun addPrompt(prompt: String, bitmap: Bitmap?) {
        updateChatList(Chats(prompt, bitmap, true))
        _chatState.update { it.copy(prompt = "", bitmap = null) }
    }

    private fun getResponse(prompt: String) {
        viewModelScope.launch {
            val chat = ChatData.getResponse(prompt)
            updateChatList(chat)
        }
    }

    private fun getResponseWithImage(prompt: String, bitmap: Bitmap) {
        viewModelScope.launch {
            val chat = ChatData.getResponseWithImage(prompt, bitmap)
            updateChatList(chat)
        }
    }
}