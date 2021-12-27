package com.signaltekno.instaclone.Screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.signaltekno.instaclone.viewmodel.HomeViewModel
import com.signaltekno.instaclone.viewmodel.SignInViewModel

@ExperimentalFoundationApi
@Composable
fun ProfileScreen(
    signInViewModel: SignInViewModel, navHostController: NavHostController, homeViewModel: HomeViewModel, setBottomBar: (Boolean)->Unit
) {
    LaunchedEffect(key1 = true) {
        setBottomBar(true)
    }

    val data by homeViewModel.profile

    val name by signInViewModel.name.collectAsState(initial = "")
    val email by signInViewModel.email.collectAsState(initial = "")
    val image by signInViewModel.email.collectAsState(initial = "")

    LaunchedEffect(key1 = name){
        homeViewModel.getProfile()
    }

    Column(modifier = Modifier
        .fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)) {
            Image(painter = rememberImagePainter(data = if(image.isEmpty()) image else "https://www.seekpng.com/png/detail/966-9665317_placeholder-image-person-jpg.png", builder = {
                transformations(CircleCropTransformation())
            }), contentDescription = "Image Profile", modifier = Modifier.size(60.dp))

            Column(modifier = Modifier.width(100.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "0", style = MaterialTheme.typography.h6)
                Text(text = "Pengikut")
            }
            Column(modifier = Modifier.width(100.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "0", style = MaterialTheme.typography.h6)
                Text(text = "Pengikut")
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 10.dp), verticalArrangement = Arrangement.Center) {
            Text(text = name, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(28.dp))
        }
        LazyVerticalGrid(cells = GridCells.Fixed(3)){
            data.data?.let {
                items(it) { item ->
                    if(item.username == name) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f), elevation = 2.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp), verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = rememberImagePainter(data = item.image),
                                    contentDescription = ""
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}