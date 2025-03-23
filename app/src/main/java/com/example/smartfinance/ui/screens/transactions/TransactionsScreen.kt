package com.example.smartfinance.ui.screens.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartfinance.data.model.Transaction
import com.example.smartfinance.data.model.TransactionType
import com.example.smartfinance.ui.components.BottomNavBar
import com.example.smartfinance.ui.components.TransactionItem
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transactions") },
                actions = {
                    IconButton(onClick = { /* Open search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /* Open filters */ }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                    }
                }
            )
        },
        bottomBar = { BottomNavBar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add transaction */ }
            ) {
                Icon(Icons.Default.Add, "Add Transaction")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Sample transactions for demonstration
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
                ),
                Transaction(
                    id = 5,
                    title = "Internet Bill",
                    amount = 59.99,
                    type = TransactionType.EXPENSE,
                    category = "Utilities",
                    date = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -7) }.time
                ),
                Transaction(
                    id = 6,
                    title = "Freelance Payment",
                    amount = 350.00,
                    type = TransactionType.INCOME,
                    category = "Freelance",
                    date = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -10) }.time
                ),
                Transaction(
                    id = 7,
                    title = "Movie Tickets",
                    amount = 24.50,
                    type = TransactionType.EXPENSE,
                    category = "Entertainment",
                    date = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -12) }.time
                )
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(sampleTransactions) { transaction ->
                    TransactionItem(transaction = transaction)
                }
            }
        }
    }
}