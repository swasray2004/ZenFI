package com.example.smartfinance.ui.screens.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen(navController: NavController) {
    var userInput by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<ChatMessage>()) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Initial greeting message
    LaunchedEffect(Unit) {
        messages = listOf(
            ChatMessage(
                "Hello! I'm your financial assistant. How can I help you today?",
                MessageType.RECEIVED
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Financial Assistant") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Chat messages
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                reverseLayout = false
            ) {
                items(messages) { message ->
                    ChatMessageItem(message)
                }
            }

            // Input field
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    placeholder = { Text("Type a message...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(8.dp))

                FloatingActionButton(
                    onClick = {
                        if (userInput.isNotBlank()) {
                            val userMessage = ChatMessage(userInput, MessageType.SENT)
                            messages = messages + userMessage
                            val inputText = userInput
                            userInput = ""

                            // Scroll to bottom
                            coroutineScope.launch {
                                listState.animateScrollToItem(messages.size - 1)
                            }

                            // Simulate AI response
                            coroutineScope.launch {
                                delay(1000) // Simulate thinking time

                                val responseText = when {
                                    inputText.contains("budget", ignoreCase = true) ->
                                        "Based on your spending patterns, I recommend allocating 30% of your income to housing, 15% to transportation, 15% to food, 10% to savings, and the rest to other categories. Would you like me to help you set up a detailed budget plan?"

                                    inputText.contains("save", ignoreCase = true) || inputText.contains("saving", ignoreCase = true) ->
                                        "Here are some ways you could save money:\n\n1. Review your subscriptions and cancel unused ones\n2. Cook at home more often\n3. Use cashback apps for your regular purchases\n4. Consider refinancing high-interest loans\n\nWould you like more specific advice based on your spending habits?"

                                    inputText.contains("invest", ignoreCase = true) ->
                                        "For beginners, I recommend starting with a diversified portfolio through index funds or ETFs. Based on your risk profile and financial goals, we can discuss more specific investment strategies. Would you like to learn more about investment options?"

                                    inputText.contains("debt", ignoreCase = true) ->
                                        "To tackle debt effectively, consider the snowball method (paying smallest debts first) or the avalanche method (focusing on highest interest rates). Looking at your accounts, you could save approximately $320 in interest by paying an extra $100 monthly toward your highest interest debt."

                                    inputText.contains("spend", ignoreCase = true) || inputText.contains("spending", ignoreCase = true) ->
                                        "Looking at your recent transactions, your highest spending categories are dining ($345 this month) and entertainment ($220). This is about 15% higher than your average. Would you like some suggestions to reduce spending in these areas?"

                                    else ->
                                        "I can help you with budgeting, saving strategies, investment advice, debt management, and analyzing your spending patterns. What specific financial topic would you like to explore?"
                                }

                                messages = messages + ChatMessage(responseText, MessageType.RECEIVED)

                                // Scroll to bottom again after response
                                listState.animateScrollToItem(messages.size - 1)
                            }
                        }
                    },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send")
                }
            }
        }
    }
}

data class ChatMessage(
    val text: String,
    val type: MessageType
)

enum class MessageType {
    SENT, RECEIVED
}

@Composable
fun ChatMessageItem(message: ChatMessage) {
    val isUserMessage = message.type == MessageType.SENT

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isUserMessage) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isUserMessage) 16.dp else 0.dp,
                        bottomEnd = if (isUserMessage) 0.dp else 16.dp
                    )
                )
                .background(
                    if (isUserMessage) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondaryContainer
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                color = if (isUserMessage) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}