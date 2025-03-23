package com.example.smartfinance.data.model

import java.util.Date

data class Transaction(
    val id: Long,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val date: Date,
    val description: String = ""
)

enum class TransactionType {
    INCOME, EXPENSE
}