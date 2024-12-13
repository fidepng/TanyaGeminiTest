package com.example.tanyagemini

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanyagemini.ui.components.ModernChatInputSection
import com.example.tanyagemini.ui.components.ModernModelChatItem
import com.example.tanyagemini.ui.components.ModernTopBar
import com.example.tanyagemini.ui.components.ModernUserChatItem
import com.example.tanyagemini.ui.theme.TanyaGeminiTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val uriState = MutableStateFlow("")

    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                uriState.update { uri.toString() }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TanyaGeminiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White // Change background to white
                ) {
                    ModernChatScreen(
                        imagePicker = imagePicker,
                        uriState = uriState
                    )
                }
            }
        }
    }

    // Existing getBitmap function
    @Composable
    fun getBitmap(uriState: MutableStateFlow<String>?): Bitmap? {
        val uri by uriState?.collectAsState() ?: remember { mutableStateOf("") }
        val context = LocalContext.current

        if (uri.isEmpty()) return null

        return try {
            val inputStream = context.contentResolver.openInputStream(Uri.parse(uri))
            inputStream?.use { stream ->
                BitmapFactory.decodeStream(stream)
            }
        } catch (e: Exception) {
            null
        }
    }
}

@Composable
fun ModernChatScreen(
    imagePicker: ActivityResultLauncher<PickVisualMediaRequest>? = null,
    uriState: MutableStateFlow<String>? = null
) {
    val chatViewModel: ChatViewModel = viewModel()
    val chatState by chatViewModel.chatState.collectAsState()

    val bitmap = (LocalContext.current as MainActivity).getBitmap(uriState)

    // Remember the list state
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Scroll to the first item when a new message is added
    LaunchedEffect(chatState.chatList.size) {
        if (chatState.chatList.isNotEmpty()) {
            coroutineScope.launch {
                // Scroll ke item pertama (karena reverseLayout = true)
                listState.scrollToItem(0)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Modern Top Bar
        ModernTopBar(title = "TanyaGemini")

        // Chat Messages List
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top,
                reverseLayout = true
            ) {
                items(chatState.chatList) { chat ->
                    if (chat.isFromUser) {
                        ModernUserChatItem(
                            prompt = chat.prompt,
                            bitmap = chat.bitmap
                        )
                    } else {
                        ModernModelChatItem(response = chat.prompt)
                    }
                }
            }

            // Floating Chat Input Section
            ModernChatInputSection(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                chatViewModel = chatViewModel,
                onImagePickerClick = {
                    imagePicker?.launch(
                        PickVisualMediaRequest
                            .Builder()
                            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            .build()
                    )
                },
                bitmap = bitmap
            )
        }
    }
}

// Preview Composables
@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun ModernChatScreenEmptyPreview() {
    val chatViewModel: ChatViewModel = viewModel()

    TanyaGeminiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ModernChatScreen()
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun ModernChatScreenWithMessagesPreview() {
    val chatViewModel: ChatViewModel = viewModel()

    // Simulate some chat messages
    chatViewModel.onEvent(ChatUiEvent.SendPrompt("Hello, can you help me?", null))
    chatViewModel.onEvent(ChatUiEvent.SendPrompt("Sure, what do you need help with?", null))

    TanyaGeminiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ModernChatScreen()
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun ModernChatScreenWithImagePreview() {
    val chatViewModel: ChatViewModel = viewModel()

    // Create a mock bitmap for preview
    val mockBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)

    // Simulate chat messages with an image
    chatViewModel.onEvent(ChatUiEvent.SendPrompt("Check out this image!", mockBitmap))
    chatViewModel.onEvent(ChatUiEvent.SendPrompt("That's an interesting image!", null))

    TanyaGeminiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ModernChatScreen()
        }
    }
}