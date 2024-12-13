package com.example.tanyagemini.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tanyagemini.ui.theme.Nunito
import com.example.tanyagemini.ui.theme.TanyaGeminiTheme

@Composable
fun ModernUserChatItem(
    prompt: String,
    bitmap: android.graphics.Bitmap? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        // Image (if exists) placed outside the chat box
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "User uploaded image",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 250.dp)
                    .padding(bottom = 8.dp),
                contentScale = ContentScale.Crop
            )
        }

        // Chat Box
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(RoundedCornerShape(32.dp, 0.dp, 32.dp, 32.dp))
                .background(Color(0xFF3369FF))
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(
                text = prompt,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Nunito
            )
        }
    }
}


@Composable
fun ModernModelChatItem(
    response: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(RoundedCornerShape(32.dp, 32.dp, 32.dp, 0.dp))
                .background(Color(0xFFF0F0F0))
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(
                text = response,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Nunito
            )
        }
    }
}

// Previews
@Preview(showBackground = false)
@Composable
fun UserChatItemPreview() {
    TanyaGeminiTheme {
        ModernUserChatItem(
            prompt = "Hello, this is a user message with some length to show how it looks.",
            bitmap = null
        )
    }
}

@Preview(showBackground = false)
@Composable
fun ModelChatItemPreview() {
    TanyaGeminiTheme {
        ModernModelChatItem(
            response = "This is a response from the AI model with some additional text to demonstrate layout."
        )
    }
}