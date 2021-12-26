package com.signaltekno.instaclone.Screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import coil.transform.CircleCropTransformation
import com.signaltekno.instaclone.R
import com.signaltekno.instaclone.navigation.Screen
import com.signaltekno.instaclone.viewmodel.HomeViewModel
import com.signaltekno.instaclone.viewmodel.SignInViewModel


@Composable
fun HomeScreen(signInViewModel: SignInViewModel, navHostController: NavHostController, homeViewModel: HomeViewModel, setBottomBar: (Boolean)->Unit) {
    val name by signInViewModel.name.collectAsState(initial = "")
    val data by homeViewModel.data
    var firstLoad by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = true) {
        setBottomBar(true)
    }

    LaunchedEffect(key1 = firstLoad){
        if(firstLoad){
            homeViewModel.getPosts()
            firstLoad = false
        }
    }

    LaunchedEffect(key1 = name){
        if(name.isNullOrEmpty()){
            navHostController.navigate(Screen.Auth.route){
                popUpTo(Screen.Auth.route){
                    inclusive = true
                }
            }
        }
    }

    data.data?.let {
        LazyColumn{
            items(it){ item ->
                Log.d("ddd data", item.toString())
                Log.d("ddd comment", item.comments.toString())
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp), elevation = 10.dp, shape = RoundedCornerShape(15.dp)) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically){
                                Image(painter = rememberImagePainter(data=item.profileImg, builder={
                                    transformations(CircleCropTransformation())
                                }), contentDescription = "Image Profile", modifier= Modifier
                                    .size(30.dp)
                                    .clip(
                                        CircleShape
                                    ), contentScale = ContentScale.Crop)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(text = item.username.toString(), fontWeight = FontWeight.Bold)
                            }
                            IconButton(onClick = {

                            }) {
                                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Options")
                            }
                        }
                        Image(painter = rememberImagePainter(data = item.image.toString(), builder = {size(OriginalSize) }), contentDescription = "Image Posts", modifier = Modifier.fillMaxWidth())
                        Row(modifier=Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                            Row() {
                                IconButton(onClick = {

                                }) {
                                    Icon(imageVector = if(item.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder, contentDescription = "Fav", tint = if(item.isLiked) Color.Red else Color.Black)
                                }
                                IconButton(onClick = {

                                }) {
                                    Icon(painter = painterResource(id = R.drawable.ic_chat), contentDescription = "Comments")
                                }
                            }
                            IconButton(onClick = {

                            }) {
                                Icon(painter = painterResource(id = R.drawable.ic_bookmark_border), contentDescription = "Bookmark")
                            }
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = item.username.toString(), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.caption)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = item.caption.toString(), maxLines = 3, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.caption)
                        }
                    }
                }
            }
        }
    }
}