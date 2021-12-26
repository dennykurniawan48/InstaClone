package com.signaltekno.instaclone

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.signaltekno.instaclone.components.CustomBottomBar
import com.signaltekno.instaclone.model.BottomNavItem
import com.signaltekno.instaclone.navigation.Screen
import com.signaltekno.instaclone.navigation.SetupNavigation
import com.signaltekno.instaclone.ui.theme.InstaCloneTheme
import com.signaltekno.instaclone.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val signInViewModel: SignInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var barShown by rememberSaveable { mutableStateOf(false) }
            val navHostController = rememberNavController()
            InstaCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            if(barShown) {
                                TopAppBar(
                                    title = {
                                        Image(
                                            painter = painterResource(id = R.drawable.logo),
                                            modifier = Modifier.width(100.dp),
                                            contentDescription = ""
                                        )
                                    },
                                    actions = {
                                        IconButton(onClick = { /*TODO*/ }) {
                                            Icon(
                                                imageVector = Icons.Default.Send,
                                                contentDescription = ""
                                            )
                                        }
                                    },
                                    backgroundColor = Color.White
                                )
                            }
                        },
                        content = {
                            Column(modifier = Modifier.fillMaxSize().padding(bottom = it.calculateBottomPadding())) {
                                SetupNavigation(navController = navHostController, signInViewModel = signInViewModel){
                                    barShown = it
                                }
                            }
                        },
                        bottomBar = {
                            if(barShown) {
                                CustomBottomBar(items = listOf(
                                    BottomNavItem(
                                        title = "Home",
                                        route = Screen.Home.route,
                                        Icons.Filled.Home
                                    ),
                                    BottomNavItem(
                                        title = "Search",
                                        route = Screen.Profile.route,
                                        Icons.Filled.Person
                                    )
                                ),
                                    navController = navHostController,
                                    onItemClick = { navHostController.navigate(it.route){
                                        popUpTo(Screen.Home.route){inclusive=true}
                                    } })
                            }
                        }
                    )
                }
            }
        }
    }
}
