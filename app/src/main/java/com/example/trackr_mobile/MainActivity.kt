package com.example.trackr_mobile

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trackr_mobile.ui.*
import com.example.trackr_mobile.ui.theme.TrackrmobileTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TrackrmobileTheme {
                MobileAds.initialize(this) {}

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
                                HomeScreen(email, displayName, applicationContext)
                            }
                        }
                    }
                }
            }
        }
    }
}
