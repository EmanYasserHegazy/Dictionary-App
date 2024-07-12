package com.user.dictionaryapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.user.dictionaryapplication.ui.screens.SearchScreen
import com.user.dictionaryapplication.ui.screens.SplashScreen


@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.Splash.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Splash.route) {
            SplashScreen(navController)
        }
        composable(NavigationItem.DictionaryScreen.route) {
            SearchScreen()
        }

    }
}