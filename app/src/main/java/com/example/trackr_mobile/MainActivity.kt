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

//                val isInEditMode = LocalInspectionMode.current
//                if (isInEditMode) {
//                    Text(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(Color.Red)
//                            .padding(horizontal = 2.dp, vertical = 6.dp),
//                        textAlign = TextAlign.Center,
//                        color = Color.White,
//                        text = "Advert Here",
//                    )
//                } else {
//                    Text(text = "Test")
//                    AndroidView(modifier = Modifier.fillMaxWidth(), factory = { context ->
//                        AdView(context).apply {
//                            setAdSize(AdSize.BANNER)
//                            adUnitId = context.getString(R.string.ad_id_banner)
//                            loadAd(AdRequest.Builder().build())
//                        }
//                    })
//                }

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
                                HomeScreen(email, displayName, applicationContext)
                            }
                        }
                    }
                }
            }
        }
    }
}

//class MainActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//        setContent {
//            TrackrmobileTheme {
//                // on below line we are specifying background color for our application
//                Surface(
//                    // on below line we are specifying modifier and color for our app
//                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
//                ) {
//
//                    // on the below line we are specifying
//                    // the theme as the scaffold.
//                    Scaffold(
//
//                        // in scaffold we are specifying the top bar.
//                        topBar = {
//
//                            // inside top bar we are specifying background color.
//                            TopAppBar(backgroundColor = Color.Green,
//
//                                // along with that we are specifying
//                                // title for our top bar.
//                                title = {
//
//                                    // in the top bar we are specifying tile as a text
//                                    Text(
//                                        // on below line we are specifying
//                                        // text to display in top app bar.
//                                        text = "GFG",
//
//                                        // on below line we are specifying
//                                        // modifier to fill max width.
//                                        modifier = Modifier.fillMaxWidth(),
//
//                                        // on below line we are specifying
//                                        // text alignment.
//                                        textAlign = TextAlign.Center,
//
//                                        // on below line we are specifying
//                                        // color for our text.
//                                        color = Color.White
//                                    )
//                                })
//                        },
//                        bottomBar = { BannersAds(LocalContext.current) },
//                        content = {
//                                  padding ->
//                            Column(modifier = Modifier.padding(padding)) {
//                                Text("Content")
//                            }
//                        },
//                    )
//                }
//            }
//        }
//    }
//}

@Composable
fun BannersAds(context: Context) {
    // on below line creating a variable for location.
    // on below line creating a column for our maps.
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // on below line we are adding a spacer.
        Spacer(modifier = Modifier.height(20.dp))
        // on below line we are adding a text
        Text(
            // on below line specifying text for heading.
            text = "Google Admob Banner Ads in Android",
            // adding text alignment,
            textAlign = TextAlign.Center,
            // on below line adding text color.
            color = Color.Green,
            // on below line adding font weight.
            fontWeight = FontWeight.Bold,
            // on below line adding padding from all sides.
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )

        // on below line adding a spacer.
        Spacer(modifier = Modifier.height(30.dp))

        // on below line adding admob banner ads.
        AndroidView(
            // on below line specifying width for ads.
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                // on below line specifying ad view.
                AdView(context).apply {
                    // on below line specifying ad size
                    setAdSize(AdSize.BANNER)
                    // on below line specifying ad unit id
                    // currently added a test ad unit id.
                    adUnitId = "ca-app-pub-3940256099942544/6300978111"
                    // calling load ad to load our ad.
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}
