package com.example.tanyagemini.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanyagemini.ChatUiEvent
import com.example.tanyagemini.ChatViewModel
import com.example.tanyagemini.R
import com.example.tanyagemini.ui.theme.TanyaGeminiTheme

@Composable
fun ModernChatInputSection(
    chatViewModel: ChatViewModel,
    onImagePickerClick: () -> Unit,
    bitmap: android.graphics.Bitmap? = null,
    modifier: Modifier = Modifier
) {
    val chatState by chatViewModel.chatState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .shadow(24.dp, RoundedCornerShape(60.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(60.dp))
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image Picker
            ImagePickerButton(
                bitmap = bitmap,
                onImagePickerClick = onImagePickerClick
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Text Input
            TextField(
                value = chatState.prompt,
                onValueChange = {
                    chatViewModel.onEvent(ChatUiEvent.UpdatePrompt(it))
                },
                placeholder = {
                    Text(
                        text = "Tanya Apa?",
                        color = Color.Black.copy(alpha = 0.5f),
                        fontSize = 13.sp
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = LocalTextStyle.current.copy(
                    color = Color.Black,
                    fontSize = 13.sp
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Send Button
            SendMessageButton(
                chatViewModel = chatViewModel,
                bitmap = bitmap
            )
        }
    }
}

@Composable
fun ImagePickerButton(
    bitmap: android.graphics.Bitmap?,
    onImagePickerClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clickable(onClick = onImagePickerClick)
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } ?: run {
            Icon(
                painter = painterResource(id = R.drawable.paperclip),
                contentDescription = "Attach Image",
                tint = Color.Black,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun SendMessageButton(
    chatViewModel: ChatViewModel,
    bitmap: android.graphics.Bitmap?
) {
    val chatState by chatViewModel.chatState.collectAsState()

    Box(
        modifier = Modifier
            .size(24.dp)
            .clickable {
                if (chatState.prompt.isNotEmpty()) {
                    chatViewModel.onEvent(ChatUiEvent.SendPrompt(chatState.prompt, bitmap))
                }
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.send),
            contentDescription = "Send Message",
            tint = Color(0xFF3369FF),
            modifier = Modifier.fillMaxSize()
        )
    }
}

// Preview untuk memudahkan pengembangan
@Preview(showBackground = false)
@Composable
fun ModernChatInputSectionPreview() {
    val chatViewModel: ChatViewModel = viewModel()

    TanyaGeminiTheme {
        ModernChatInputSection(
            chatViewModel = chatViewModel,
            onImagePickerClick = {},
            bitmap = null
        )
    }
}