package com.example.smartfinance.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartfinance.ui.screens.dashboard.DashboardScreen
import com.example.smartfinance.ui.screens.transactions.TransactionsScreen
import com.example.smartfinance.ui.screens.budget.BudgetScreen
import com.example.smartfinance.ui.screens.insights.InsightsScreen
import com.example.smartfinance.ui.screens.settings.SettingsScreen
import com.example.smartfinance.ui.screens.receipt.ScanReceiptScreen
import com.example.smartfinance.ui.screens.chatbot.ChatbotScreen
import com.example.smartfinance.ui.screens.splash.SplashContent

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var showSplashContent by remember { mutableStateOf(true) }

    NavHost(navController = navController, startDestination = "splash_content") {
        // Additional animated splash content
        composable("splash_content") {
            SplashContent(
                onSplashFinished = {
                    showSplashContent = false
                    // Navigate to the appropriate destination based on login state
                    navController.navigate("dashboard") {
                        popUpTo("splash_content") { inclusive = true }
                    }
                }
            )
        }
        composable("dashboard") {
            DashboardScreen(navController = navController)
        }
        composable("transactions") {
            TransactionsScreen(navController = navController)
        }
        composable("budget") {
            BudgetScreen(navController = navController)
        }
        composable("insights") {
            InsightsScreen(navController = navController)
        }
        composable("settings") {
            SettingsScreen(navController = navController)
        }
        composable("scan_receipt") {
            ScanReceiptScreen(navController = navController)
        }
        composable("chatbot") {
            ChatbotScreen(navController = navController)
        }
    }
}