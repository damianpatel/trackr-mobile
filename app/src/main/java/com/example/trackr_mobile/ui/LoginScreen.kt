package com.example.trackr_mobile.ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AuthPage(navController: NavController) {

    Button(onClick = {
        navController.navigate(Screen.HOME.route)
    }) {
        Text(text = "Sign in with Google")
    }
}