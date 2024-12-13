package com.example.tanyagemini.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun ModernTopBar(
    title: String = "TanyaGemini",
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onAddClick: () -> Unit = {} // New parameter for Add button
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Menu Icon and Title (Left-aligned)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Menu Icon
                    Icon(
                        painter = painterResource(id = R.drawable.menu),
                        contentDescription = "Menu",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(onClick = onMenuClick),
                        tint = Color.Black
                    )

                    // Spacer between menu icon and title
                    Spacer(modifier = Modifier.width(16.dp))

                    // Title
                    Text(
                        text = title,
                        color = Color(0xFF3369FF),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Nunito
                    )
                }

                // Right-side Icons
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Add (+) Icon
                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Add",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(onClick = onAddClick),
                        tint = Color.Black
                    )

                    // Spacer between Add and Trash icons
                    Spacer(modifier = Modifier.width(16.dp))

                    // Trash/Delete Icon
                    Icon(
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = "Delete",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(onClick = onDeleteClick),
                        tint = Color.Black
                    )
                }
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

// Preview
@Preview(showBackground = true)
@Composable
fun ModernTopBarPreview() {
    TanyaGeminiTheme {
        ModernTopBar()
    }
}