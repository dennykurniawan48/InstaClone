package com.signaltekno.instaclone.Screen

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.signaltekno.instaclone.R
import com.signaltekno.instaclone.navigation.Screen
import com.signaltekno.instaclone.viewmodel.SignInViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    signInViewModel: SignInViewModel,
    setBottomBar: (Boolean) -> Unit
) {

    LaunchedEffect(key1 = true){
        setBottomBar(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val email by signInViewModel.email.collectAsState("")
        LaunchedEffect(key1 = email){
            delay(3000L)
            if(email.isNullOrEmpty()){
                navHostController.navigate(Screen.Auth.route){
                    popUpTo(Screen.Splash.route){inclusive=true}
                }
            }else{
                navHostController.navigate(Screen.Home.route){
                    popUpTo(Screen.Splash.route){inclusive=true}
                }
            }
        }
        Image(painter = painterResource(id = R.drawable.icon), contentDescription = "App Logo", modifier = Modifier.size(100.dp))
    }
}