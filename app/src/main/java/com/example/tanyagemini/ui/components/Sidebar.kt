package com.example.tanyagemini.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tanyagemini.R
import com.example.tanyagemini.ui.theme.Nunito
import com.example.tanyagemini.ui.theme.TanyaGeminiTheme

@Composable
fun Sidebar(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onNewChat: () -> Unit,
    onHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxHeight()
            .width(300.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp)
            ),
        color = Color.White,
        shape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Close Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClose),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = "Close Sidebar",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Menu",
                    fontFamily = Nunito,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3369FF)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            Divider(color = Color(0xFFEEEEEE), thickness = 2.dp)
            Spacer(modifier = Modifier.height(32.dp))

            // Sidebar Menu Items
            SidebarMenuItem(
                icon = R.drawable.add,
                text = "New Chat",
                onClick = onNewChat
            )

            Spacer(modifier = Modifier.height(24.dp))

            SidebarMenuItem(
                icon = R.drawable.menu,
                text = "Chat History",
                onClick = onHistoryClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            SidebarMenuItem(
                icon = R.drawable.settings,
                text = "Settings",
                onClick = onSettingsClick
            )

            // Spacer to push items to the top
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun SidebarMenuItem(
    icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = text,
            modifier = Modifier.size(24.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontFamily = Nunito,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
}

// Preview Composable
@Preview(showBackground = true)
@Composable
fun SidebarPreview() {
    TanyaGeminiTheme {
        Sidebar(
            onClose = {},
            onNewChat = {},
            onHistoryClick = {},
            onSettingsClick = {}
        )
    }
}