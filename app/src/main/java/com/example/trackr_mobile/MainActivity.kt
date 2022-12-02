package com.example.trackr_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trackr_mobile.ui.AuthPage
import com.example.trackr_mobile.ui.AuthScreen
import com.example.trackr_mobile.ui.HomeScreen
import com.example.trackr_mobile.ui.Screen
import com.example.trackr_mobile.ui.theme.TrackrmobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrackrmobileTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    //AuthScreen()

                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Screen.LOGIN.route ) {
                        composable(Screen.LOGIN.route) {
                            AuthScreen(navController = navController)
                        }
                        composable("${Screen.HOME.route}/{email}/{displayName}") {
                            val email = it.arguments?.getString("email")
                            val displayName = it.arguments?.getString("displayName")


                            if (email != null && displayName != null) {
                                HomeScreen(email, displayName)
                            }
                        }
                    }
                }
            }
        }
    }
}
