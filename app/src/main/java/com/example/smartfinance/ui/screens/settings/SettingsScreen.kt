package com.example.smartfinance.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartfinance.R
import com.example.smartfinance.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image - replace 'settings_background' with your actual image resource name
        Image(
            painter = painterResource(id = R.drawable.blue),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.6f // Adjust transparency of the background
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Settings", color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White
                    )
                )
            },
            bottomBar = {
                BottomNavBar(
                    navController = navController,
                    modifier = Modifier.background(Color.Transparent)
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Profile Section
                SettingsSection(
                    title = "Profile",
                    icon = Icons.Default.Person,
                    items = listOf(
                        SettingsItem("Personal Information", { /* Open personal info */ }),
                        SettingsItem("Account Settings", { /* Open account settings */ })
                    )
                )

                // Notifications Section
                var notificationsEnabled by remember { mutableStateOf(true) }

                SettingsSection(
                    title = "Notifications",
                    icon = Icons.Default.Notifications,
                    items = listOf(
                        SettingsItem(
                            "Enable Notifications",
                            onClick = { },
                            trailing = {
                                Switch(
                                    checked = notificationsEnabled,
                                    onCheckedChange = { notificationsEnabled = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                                    )
                                )
                            }
                        ),
                        SettingsItem("Notification Preferences", { /* Open notification preferences */ })
                    )
                )

                // Appearance Section
                var darkModeEnabled by remember { mutableStateOf(false) }

                SettingsSection(
                    title = "Appearance",
                    icon = Icons.Default.Palette,
                    items = listOf(
                        SettingsItem(
                            "Dark Mode",
                            onClick = { },
                            trailing = {
                                Switch(
                                    checked = darkModeEnabled,
                                    onCheckedChange = { darkModeEnabled = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                                    )
                                )
                            }
                        ),
                        SettingsItem("Theme", { /* Open theme settings */ })
                    )
                )

                // Security Section
                SettingsSection(
                    title = "Security",
                    icon = Icons.Default.Security,
                    items = listOf(
                        SettingsItem("Change Password", { /* Open change password */ }),
                        SettingsItem("Biometric Authentication", { /* Open biometric settings */ }),
                        SettingsItem("Privacy Settings", { /* Open privacy settings */ })
                    )
                )

                // Language Section
                SettingsSection(
                    title = "Language",
                    icon = Icons.Default.Language,
                    items = listOf(
                        SettingsItem("App Language", { /* Open language settings */ })
                    )
                )

                // About Section
                SettingsSection(
                    title = "About",
                    icon = Icons.Default.Info,
                    items = listOf(
                        SettingsItem("App Version", { /* Show app version */ }),
                        SettingsItem("Terms of Service", { /* Open terms of service */ }),
                        SettingsItem("Privacy Policy", { /* Open privacy policy */ })
                    )
                )

                // Logout Button
                Button(
                    onClick = { /* Logout */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                ) {
                    Text("Logout", color = Color.White)
                }
            }
        }
    }
}

data class SettingsItem(
    val title: String,
    val onClick: () -> Unit,
    val trailing: @Composable (() -> Unit)? = null
)

@Composable
fun SettingsSection(
    title: String,
    icon: ImageVector,
    items: List<SettingsItem>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            // Section Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Section Items
            items.forEach { item ->
                ListItem(
                    headlineContent = {
                        Text(
                            item.title,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                        )
                    },
                    trailingContent = item.trailing,
                    modifier = Modifier.clickable { item.onClick() }
                )

                if (item != items.last()) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                    )
                }
            }
        }
    }
}