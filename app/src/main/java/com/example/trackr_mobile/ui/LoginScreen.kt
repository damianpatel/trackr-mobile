package com.example.trackr_mobile.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.trackr_mobile.model.AuthViewModel
import com.example.trackr_mobile.util.AuthResultContract
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

@Composable
fun AuthPage(text: String = "", loadingText: String = "", onClick:() -> Unit) {
    var clicked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            clicked = !clicked
            onClick()
        }) {
            Text(text = "Sign in with Google")
        }

        AdBanner(context = LocalContext.current)
    }
}

@Composable
fun AuthScreen(viewModel: AuthViewModel = AuthViewModel(), navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf<String?>(null) }
    val user by remember(viewModel) { viewModel.user }.collectAsState()
    val requestCode = 1
    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) {
            task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                println(account)
                if (account == null) {
                    text = "Sign in Failure"
                } else {
                    coroutineScope.launch {
                        account.email?.let { account.displayName?.let { it1 -> viewModel.signIn(email = it, displayName = it1) } }
                    }
                }
            } catch (e: ApiException) {
                text = "Sign In Failure"
            }
        }

    AuthPage(onClick = { authResultLauncher.launch(requestCode) })
    user?.let {

        navController.navigate("${Screen.HOME.route}/${it.email}/${it.displayName}")
    }
}