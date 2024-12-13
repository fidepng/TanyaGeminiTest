package com.example.tanyagemini.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tanyagemini.R
import com.example.tanyagemini.data.Conversation
import com.example.tanyagemini.ui.theme.Nunito
import com.example.tanyagemini.ui.theme.TanyaGeminiTheme

@Composable
fun ModernTopBar(
    title: String = "TanyaGemini",
    conversations: List<Conversation> = emptyList(),
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onConversationSelect: (Conversation) -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    var showSidebar by remember { mutableStateOf(false) }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Menu Icon and Title (Left-aligned)
                androidx.compose.foundation.layout.Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Menu Icon
                    Icon(
                        painter = painterResource(id = R.drawable.menu),
                        contentDescription = "Menu",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                showSidebar = !showSidebar
                                onMenuClick()
                            },
                        tint = Color.Black
                    )

                    // Spacer between menu icon and title
                    androidx.compose.foundation.layout.Spacer(
                        modifier = Modifier.width(16.dp)
                    )

                    // Title
                    Text(
                        text = title,
                        color = Color(0xFF3369FF),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Nunito
                    )
                }

                // Trash/Delete Icon (Right)
                Icon(
                    painter = painterResource(id = R.drawable.trash),
                    contentDescription = "Delete",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onDeleteClick),
                    tint = Color.Black
                )
            }

            // Sidebar
            if (showSidebar) {
                ConversationSidebar(
                    conversations = conversations,
                    onConversationSelect = {
                        onConversationSelect(it)
                        showSidebar = false
                    }
                )
            }
        }

        // Horizontal Line
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = Color(0xFFEEEEEE)
        )
    }
}

@Composable
fun ConversationSidebar(
    conversations: List<Conversation>,
    onConversationSelect: (Conversation) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(250.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {
        LazyColumn {
            items(conversations) { conversation ->
                Text(
                    text = conversation.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onConversationSelect(conversation) }
                        .padding(vertical = 8.dp)
                )
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            }
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun ModernTopBarPreview() {
    TanyaGeminiTheme {
        ModernTopBar()
    }
}