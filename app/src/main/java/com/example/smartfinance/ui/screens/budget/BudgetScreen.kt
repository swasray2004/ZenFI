package com.example.smartfinance.ui.screens.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartfinance.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    // Mutable list of budget categories
    var budgetCategories by remember { mutableStateOf(listOf<BudgetCategory>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budget") }
            )
        },
        bottomBar = { BottomNavBar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true } // Show the input dialog
            ) {
                Icon(Icons.Default.Add, "Add Budget Category")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Monthly Budget Overview
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Monthly Budget",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val totalSpent = budgetCategories.sumOf { it.spent }
                    val totalBudget = budgetCategories.sumOf { it.limit }
                    val progress = if (totalBudget > 0) (totalSpent / totalBudget).coerceIn(0.0, 1.0).toFloat() else 0f

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Spent: Rs.${String.format("%.2f", totalSpent)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Remaining: Rs.${String.format("%.2f", totalBudget - totalSpent)}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "Total: Rs.${String.format("%.2f", totalBudget)}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }

            // Budget Categories
            Text(
                text = "Budget Categories",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn {
                items(budgetCategories) { category ->
                    BudgetCategoryItem(category)
                }
            }
        }
    }

    // Show the input dialog
    if (showDialog) {
        AddBudgetCategoryDialog(
            onDismiss = { showDialog = false },
            onSave = { newCategory ->
                budgetCategories = budgetCategories + newCategory
                showDialog = false
            }
        )
    }
}

data class BudgetCategory(
    val name: String,
    val limit: Double,
    val spent: Double
)

@Composable
fun BudgetCategoryItem(category: BudgetCategory) {
    val progress = (category.spent / category.limit).coerceIn(0.0, 1.0).toFloat()
    val isOverBudget = category.spent > category.limit

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "Rs.${String.format("%.2f", category.spent)} / Rs.${String.format("%.2f", category.limit)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            val progressColor = when {
                progress < 0.5f -> MaterialTheme.colorScheme.primary // Green for safe spending
                progress < 0.8f -> MaterialTheme.colorScheme.tertiary // Yellow for caution
                else -> MaterialTheme.colorScheme.error // Red for over-budget
            }

            LinearProgressIndicator(
                progress = { progress }, // Use lambda instead of direct Float value
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = progressColor,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )



            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Remaining: Rs.${String.format("%.2f", category.limit - category.spent)}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBudgetCategoryDialog(onDismiss: () -> Unit, onSave: (BudgetCategory) -> Unit) {
    var name by remember { mutableStateOf("") }
    var limit by remember { mutableStateOf("") }
    var spent by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Budget Category") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Category Name") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = limit,
                    onValueChange = { limit = it },
                    label = { Text("Budget Limit (Rs.)") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = spent,
                    onValueChange = { spent = it },
                    label = { Text("Amount Spent (Rs.)") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val limitValue = limit.toDoubleOrNull() ?: 0.0
                    val spentValue = spent.toDoubleOrNull() ?: 0.0
                    if (name.isNotBlank() && limitValue > 0) {
                        onSave(BudgetCategory(name, limitValue, spentValue))
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
