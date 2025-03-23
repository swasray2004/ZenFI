package com.example.smartfinance.ui.screens.receipt

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanReceiptScreen(navController: NavController) {
    var scanningState by remember { mutableStateOf(ScanningState.READY) }
    var extractedData by remember { mutableStateOf<ReceiptData?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan Receipt") },
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (scanningState) {
                ScanningState.READY -> {
                    // Camera preview placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(bottom = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Camera Preview")
                            }
                        }
                    }

                    Text(
                        text = "Position the receipt within the frame",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Button(
                        onClick = {
                            scanningState = ScanningState.PROCESSING
                            // Simulate processing delay
                            // In a real app, this would trigger the camera to take a photo
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = "Scan",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Scan Receipt")
                    }
                }

                ScanningState.PROCESSING -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(bottom = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(80.dp)
                        )
                    }

                    Text(
                        text = "Processing receipt...",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Simulate processing completion after a delay
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(2000)
                        extractedData = ReceiptData(
                            merchant = "Grocery Store",
                            date = "2023-05-15",
                            total = 78.35,
                            items = listOf(
                                ReceiptItem("Milk", 3.99),
                                ReceiptItem("Bread", 2.49),
                                ReceiptItem("Eggs", 4.99),
                                ReceiptItem("Apples", 5.49),
                                ReceiptItem("Chicken", 12.99),
                                ReceiptItem("Pasta", 1.99),
                                ReceiptItem("Tomatoes", 3.49)
                            )
                        )
                        scanningState = ScanningState.COMPLETED
                    }
                }

                ScanningState.COMPLETED -> {
                    extractedData?.let { data ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Receipt Details",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Merchant:")
                                    Text(data.merchant, fontWeight = FontWeight.Medium)
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Date:")
                                    Text(data.date, fontWeight = FontWeight.Medium)
                                }

                                Divider(modifier = Modifier.padding(vertical = 8.dp))

                                Text(
                                    text = "Items",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )

                                data.items.forEach { item ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 2.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(item.name)
                                        Text("$${String.format("%.2f", item.price)}")
                                    }
                                }

                                Divider(modifier = Modifier.padding(vertical = 8.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "Total:",
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        "$${String.format("%.2f", data.total)}",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Text(
                            text = "Category: Groceries",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        Button(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Save",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text("Save Transaction")
                        }

                        TextButton(
                            onClick = { scanningState = ScanningState.READY },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Text("Scan Again")
                        }
                    }
                }
            }
        }
    }
}

enum class ScanningState {
    READY, PROCESSING, COMPLETED
}

data class ReceiptData(
    val merchant: String,
    val date: String,
    val total: Double,
    val items: List<ReceiptItem>
)

data class ReceiptItem(
    val name: String,
    val price: Double
)