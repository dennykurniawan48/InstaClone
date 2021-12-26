package com.signaltekno.instaclone.navigation

sealed class Screen(val route: String){
    object Splash: Screen("SPLASH")
    object Auth: Screen("AUTH")
    object Home: Screen("HOME")
    object Profile: Screen("PROFILE")
}
