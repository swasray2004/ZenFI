package com.example.smartfinance.data.repository

import com.example.smartfinance.data.model.Transaction
import com.example.smartfinance.data.model.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.*

/**
 * Repository for managing transaction data.
 * In a real app, this would interact with a local database and possibly a remote API.
 */
class TransactionRepository {
    private val transactionsFlow = MutableStateFlow<List<Transaction>>(emptyList())

    init {
        // Initialize with sample data for demonstration
        val sampleTransactions = generateSampleTransactions()
        transactionsFlow.value = sampleTransactions
    }

    fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionsFlow
    }

    fun getTransactionsByType(type: TransactionType): Flow<List<Transaction>> {
        return transactionsFlow.map { transactions ->
            transactions.filter { it.type == type }
        }
    }

    fun getTransactionsByCategory(category: String): Flow<List<Transaction>> {
        return transactionsFlow.map { transactions ->
            transactions.filter { it.category == category }
        }
    }

    fun getTransactionsByDateRange(startDate: Date, endDate: Date): Flow<List<Transaction>> {
        return transactionsFlow.map { transactions ->
            transactions.filter { it.date in startDate..endDate }
        }
    }

    suspend fun addTransaction(transaction: Transaction) {
        val currentList = transactionsFlow.value.toMutableList()
        currentList.add(transaction)
        transactionsFlow.value = currentList
    }

    suspend fun updateTransaction(transaction: Transaction) {
        val currentList = transactionsFlow.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == transaction.id }
        if (index != -1) {
            currentList[index] = transaction
            transactionsFlow.value = currentList
        }
    }

    suspend fun deleteTransaction(transactionId: Long) {
        val currentList = transactionsFlow.value.toMutableList()
        currentList.removeIf { it.id == transactionId }
        transactionsFlow.value = currentList
    }

    private fun generateSampleTransactions(): List<Transaction> {
        val calendar = Calendar.getInstance()
        val transactions = mutableListOf<Transaction>()

        // Add sample transactions spanning the last 3 months
        for (i in 0 until 50) {
            val daysAgo = (0..90).random()
            calendar.time = Date()
            calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

            val type = if (i % 5 == 0) TransactionType.INCOME else TransactionType.EXPENSE
            val category = when {
                type == TransactionType.INCOME -> listOf("Salary", "Freelance", "Investments").random()
                else -> listOf("Groceries", "Dining", "Transportation", "Entertainment", "Utilities", "Shopping", "Health").random()
            }

            val amount = when {
                type == TransactionType.INCOME -> (1000..3000).random().toDouble() + (0..99).random() / 100.0
                category == "Groceries" -> (30..150).random().toDouble() + (0..99).random() / 100.0
                category == "Dining" -> (15..80).random().toDouble() + (0..99).random() / 100.0
                category == "Transportation" -> (10..60).random().toDouble() + (0..99).random() / 100.0
                category == "Entertainment" -> (20..100).random().toDouble() + (0..99).random() / 100.0
                category == "Utilities" -> (50..200).random().toDouble() + (0..99).random() / 100.0
                category == "Shopping" -> (20..200).random().toDouble() + (0..99).random() / 100.0
                category == "Health" -> (30..150).random().toDouble() + (0..99).random() / 100.0
                else -> (10..100).random().toDouble() + (0..99).random() / 100.0
            }

            val title = when (category) {
                "Salary" -> "Monthly Salary"
                "Freelance" -> "Freelance Payment"
                "Investments" -> "Investment Return"
                "Groceries" -> listOf("Grocery Store", "Supermarket", "Local Market").random()
                "Dining" -> listOf("Restaurant", "Coffee Shop", "Fast Food").random()
                "Transportation" -> listOf("Gas Station", "Uber", "Public Transit").random()
                "Entertainment" -> listOf("Movie Theater", "Concert", "Streaming Service").random()
                "Utilities" -> listOf("Electric Bill", "Water Bill", "Internet Bill").random()
                "Shopping" -> listOf("Department Store", "Online Shopping", "Electronics Store").random()
                "Health" -> listOf("Pharmacy", "Doctor Visit", "Gym Membership").random()
                else -> "Miscellaneous"
            }

            transactions.add(
                Transaction(
                    id = i.toLong(),
                    title = title,
                    amount = amount,
                    type = type,
                    category = category,
                    date = calendar.time,
                    description = "Transaction description"
                )
            )
        }

        return transactions.sortedByDescending { it.date }
    }
}
