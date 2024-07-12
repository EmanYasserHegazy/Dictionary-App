package com.user.dictionaryapplication.ui.navigation

sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem(Screen.SPLASH.name)
    object DictionaryScreen : NavigationItem(Screen.DictionaryScreen.name)
}

enum class Screen {
    SPLASH,
    DictionaryScreen,
}
