package com.example.smartfinance.ml

import com.example.smartfinance.data.model.Transaction
import com.example.smartfinance.data.model.TransactionType
import java.util.*

/**
 * This class analyzes spending patterns and provides insights and recommendations.
 * In a real app, this would use more sophisticated ML models for pattern recognition.
 */
class SpendingAnalyzer {

    fun analyzeSpendingTrends(transactions: List<Transaction>, months: Int = 3): List<SpendingInsight> {
        val insights = mutableListOf<SpendingInsight>()

        // Group transactions by category
        val categorizedTransactions = transactions
            .filter { it.type == TransactionType.EXPENSE }
            .groupBy { it.category }

        // Calculate total spending by category
        val categoryTotals = categorizedTransactions.mapValues { (_, transactions) ->
            transactions.sumOf { it.amount }
        }

        // Find top spending categories
        val topCategories = categoryTotals.entries
            .sortedByDescending { it.value }
            .take(3)

        // Generate insights for top categories
        topCategories.forEach { (category, total) ->
            insights.add(
                SpendingInsight(
                    category = category,
                    message = "You've spent Rs.${String.format("%.2f", total)} on $category in the last $months months.",
                    insightType = InsightType.SPENDING_SUMMARY
                )
            )
        }

        // Simulate trend analysis
        if (categoryTotals.containsKey("Dining")) {
            insights.add(
                SpendingInsight(
                    category = "Dining",
                    message = "Your dining expenses have increased by 12% compared to last month.",
                    insightType = InsightType.TREND
                )
            )
        }

        if (categoryTotals.containsKey("Entertainment")) {
            insights.add(
                SpendingInsight(
                    category = "Entertainment",
                    message = "You could save approximately Rs. 45 per month by reducing entertainment expenses.",
                    insightType = InsightType.SAVING_OPPORTUNITY
                )
            )
        }

        // Add general insights
        insights.add(
            SpendingInsight(
                category = "General",
                message = "Setting up automatic transfers to your savings account can help you reach your goals faster.",
                insightType = InsightType.RECOMMENDATION
            )
        )

        return insights
    }

    fun detectAnomalies(transactions: List<Transaction>): List<Transaction> {
        // In a real app, this would use statistical methods or ML to detect unusual transactions
        // For demonstration, we'll use a simple threshold approach

        val recentTransactions = transactions.sortedByDescending { it.date }
        val expenseTransactions = recentTransactions.filter { it.type == TransactionType.EXPENSE }

        // Calculate average transaction amount
        val averageAmount = if (expenseTransactions.isNotEmpty()) {
            expenseTransactions.sumOf { it.amount } / expenseTransactions.size
        } else {
            0.0
        }

        // Flag transactions that are significantly higher than average
        return expenseTransactions.filter { it.amount > averageAmount * 2 }
    }
}

data class SpendingInsight(
    val category: String,
    val message: String,
    val insightType: InsightType
)

enum class InsightType {
    SPENDING_SUMMARY, TREND, SAVING_OPPORTUNITY, RECOMMENDATION, ANOMALY
}