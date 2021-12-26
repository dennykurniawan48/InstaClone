package com.signaltekno.instaclone.Screen

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.common.api.ApiException
import com.signaltekno.instaclone.R
import com.signaltekno.instaclone.components.GoogleSigninButton
import com.signaltekno.instaclone.navigation.Screen
import com.signaltekno.instaclone.utils.AuthResult
import com.signaltekno.instaclone.viewmodel.SignInViewModel

@Composable
fun AuthScreen(
    signInViewModel: SignInViewModel,
    navHostController: NavHostController
) {
    var isLoading by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf<String?>(null) }
    val signInRequestCode = 1
    val email by signInViewModel.email.collectAsState(initial = "")
    Log.d("email", email)
    LaunchedEffect(key1 = email) {
        Log.d("email", email)
        if (!email.isNullOrEmpty()) {
            navHostController.navigate(Screen.Home.route){
                popUpTo(Screen.Auth.route){inclusive = true}
            }
        }
    }

    val authResultLauncher = rememberLauncherForActivityResult(contract = AuthResult()) { task ->
        try {
            val account = task?.getResult(ApiException::class.java)
            if (account == null) {
                text = "Google Sign In Failed"
            } else {
                val img = if(account.photoUrl == null) "https://icon-library.com/images/no-profile-picture-icon/no-profile-picture-icon-27.jpg" else account.photoUrl.toString()
                signInViewModel.setSignInValue(
                    email = account.email!!,
                    displayName = account.displayName!!,
                    userImg = img
                )
                isLoading = false
            }
        } catch (e: ApiException) {
            text = e.localizedMessage
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo", Modifier.width(200.dp))
        Spacer(modifier = Modifier.height(150.dp))
        GoogleSigninButton(
            text = "Sign In with Google",
            icon = painterResource(id = R.drawable.ic_btn_signin_google),
            loadingText = "Signing In...",
            isLoading = isLoading,
            onClick = {
                isLoading = true
                text = null
                authResultLauncher.launch(signInRequestCode)
            }
        )

        text?.let {
            isLoading = false

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = it
            )
        }
    }
}