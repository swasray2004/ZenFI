package com.example.smartfinance.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartfinance.data.model.Transaction
import com.example.smartfinance.data.model.TransactionType
import com.example.smartfinance.ui.theme.PositiveGreen
import com.example.smartfinance.ui.theme.NegativeRed
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category Icon
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = getCategoryIcon(transaction.category),
                        contentDescription = transaction.category,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Transaction details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "${transaction.category} • ${formatDate(transaction.date)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Amount
            Text(
                text = formatAmount(transaction.amount, transaction.type),
                style = MaterialTheme.typography.bodyLarge,
                color = if (transaction.type == TransactionType.INCOME) PositiveGreen else NegativeRed,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun getCategoryIcon(category: String): ImageVector {
    return when (category.lowercase()) {
        "groceries" -> Icons.Default.LocalGroceryStore
        "dining" -> Icons.Default.Fastfood
        "transportation" -> Icons.Default.DirectionsCar
        "salary" -> Icons.Default.Work
        else -> Icons.Default.Payments
    }
}

fun formatAmount(amount: Double, type: TransactionType): String {
    return when (type) {
        TransactionType.INCOME -> "+$${String.format("%.2f", amount)}"
        TransactionType.EXPENSE -> "-$${String.format("%.2f", amount)}"
    }
}

fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("MMM dd", Locale.getDefault())
    return formatter.format(date)
}