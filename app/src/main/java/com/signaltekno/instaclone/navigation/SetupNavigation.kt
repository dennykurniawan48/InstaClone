package com.signaltekno.instaclone.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.signaltekno.instaclone.Screen.AuthScreen
import com.signaltekno.instaclone.Screen.HomeScreen
import com.signaltekno.instaclone.Screen.ProfileScreen
import com.signaltekno.instaclone.Screen.SplashScreen
import com.signaltekno.instaclone.viewmodel.SignInViewModel

@Composable
fun SetupNavigation(
    navController: NavHostController,
    signInViewModel: SignInViewModel,
    setBottomBar: (Boolean) -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navHostController =navController, signInViewModel = signInViewModel, setBottomBar)
        }
        composable(Screen.Auth.route) {
            AuthScreen(signInViewModel = signInViewModel, navHostController = navController)
        }
        composable(Screen.Home.route){
            HomeScreen(signInViewModel, homeViewModel = hiltViewModel(), navHostController = navController, setBottomBar = setBottomBar)
        }
        composable(Screen.Profile.route){
            ProfileScreen(
                signInViewModel = signInViewModel,
                navHostController = navController,
                homeViewModel = hiltViewModel(),
                setBottomBar =setBottomBar
            )
        }
    }
}