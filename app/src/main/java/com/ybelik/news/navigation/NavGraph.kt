package com.ybelik.news.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ybelik.news.screen.settings.SettingsScreen
import com.ybelik.news.screen.subscriiption.SubscriptionsScreen

sealed class Screen(val route: String) {
    object SubscriptionsScreen : Screen("subscriptions")
    object SettingsScreen : Screen("settings")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SubscriptionsScreen.route
    ) {
        composable(Screen.SubscriptionsScreen.route) {
            SubscriptionsScreen(
                onNavigateToSettings = {
                    navController.navigate(Screen.SettingsScreen.route)
                },
            )
        }
        composable(Screen.SettingsScreen.route) {
            SettingsScreen(
                onGoBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}