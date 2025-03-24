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
    var transactions by remember { mutableStateOf(listOf<Transaction>()) }
    var showDialog by remember { mutableStateOf(false) }

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
                onClick = { showDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(transactions) { transaction ->
                    TransactionItem(transaction = transaction)
                }
            }
        }
    }

    if (showDialog) {
        AddTransactionDialog(
            onDismiss = { showDialog = false },
            onAddTransaction = { newTransaction ->
                transactions = transactions + newTransaction
            }
        )
    }
}

@Composable
fun AddTransactionDialog(onDismiss: () -> Unit, onAddTransaction: (Transaction) -> Unit) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(TransactionType.EXPENSE) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Transaction") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Type: ")
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { type = TransactionType.INCOME }) {
                        Text("Income")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { type = TransactionType.EXPENSE }) {
                        Text("Expense")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val transaction = Transaction(
                        id = System.currentTimeMillis(),
                        title = title,
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        category = category,
                        type = type,
                        date = Date()
                    )
                    onAddTransaction(transaction)
                    onDismiss()
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
