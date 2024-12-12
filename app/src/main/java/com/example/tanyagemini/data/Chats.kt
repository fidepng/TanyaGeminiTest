package com.example.tanyagemini.data

import coil3.Bitmap

data class Chats (
    val prompt: String,
    val bitmap: Bitmap?,
    val isFromUser: Boolean
)