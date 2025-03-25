package com.example.smartfinance.ui.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartfinance.R
import com.example.smartfinance.ui.components.BottomNavBar
import com.example.smartfinance.ui.components.TransactionItem
import com.example.smartfinance.ui.theme.PositiveGreen
import com.example.smartfinance.ui.theme.NegativeRed
import com.example.smartfinance.data.model.Transaction
import com.example.smartfinance.data.model.TransactionType
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image - replace 'background_image' with your actual image resource name
        Image(
            painter = painterResource(id = R.drawable.blue),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.7f // Adjust transparency of the background
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("SmartFinance", color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White
                    ),
                    actions = {
                        IconButton(onClick = { navController.navigate("chatbot") }) {
                            Icon(
                                Icons.Default.Chat,
                                contentDescription = "Financial Assistant",
                                tint = Color.White
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavBar(
                    navController = navController,
                    modifier = Modifier.background(Color.Transparent)
                )
            },
            floatingActionButton = {
                Row {
                    SmallFloatingActionButton(
                        onClick = { navController.navigate("scan_receipt") },
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f),
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(Icons.Default.CameraAlt, "Scan Receipt")
                    }
                    FloatingActionButton(
                        onClick = { /* Add transaction */ },
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    ) {
                        Icon(Icons.Default.Add, "Add Transaction")
                    }
                }
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Balance Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Total Balance",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "$4,285.75",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Income",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "$6,240.00",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = PositiveGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Expenses",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "$1,954.25",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = NegativeRed,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // AI Insights Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "AI Insights",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "You've spent 15% less on dining this month compared to last month. Great job!",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Tip: Consider setting up automatic transfers to your savings account to reach your vacation goal faster.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Button(
                            onClick = { navController.navigate("insights") },
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(top = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                            )
                        ) {
                            Text("View All Insights")
                        }
                    }
                }

                // Recent Transactions
                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.White
                )

                // Sample transactions
                val sampleTransactions = listOf(
                    Transaction(
                        id = 1,
                        title = "Grocery Store",
                        amount = 78.35,
                        type = TransactionType.EXPENSE,
                        category = "Groceries",
                        date = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }.time
                    ),
                    Transaction(
                        id = 2,
                        title = "Salary Deposit",
                        amount = 2450.00,
                        type = TransactionType.INCOME,
                        category = "Salary",
                        date = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -3) }.time
                    ),
                    Transaction(
                        id = 3,
                        title = "Coffee Shop",
                        amount = 4.75,
                        type = TransactionType.EXPENSE,
                        category = "Dining",
                        date = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -3) }.time
                    ),
                    Transaction(
                        id = 4,
                        title = "Gas Station",
                        amount = 45.50,
                        type = TransactionType.EXPENSE,
                        category = "Transportation",
                        date = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -5) }.time
                    )
                )

                sampleTransactions.forEach { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                    )
                }

                TextButton(
                    onClick = { navController.navigate("transactions") },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                ) {
                    Text("View All Transactions", color = Color.White)
                }
            }
        }
    }
}