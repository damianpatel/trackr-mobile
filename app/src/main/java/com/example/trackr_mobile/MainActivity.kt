package com.example.trackr_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
                    TitleBar(title = "TrackR")
                }
            }
        }
    }
}

@Composable
fun TitleBar(title: String) {
    TopAppBar(title = {
        Text(text = title, textAlign = TextAlign.Center)
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TrackrmobileTheme {
        TitleBar(title = "TrackR")
    }
}