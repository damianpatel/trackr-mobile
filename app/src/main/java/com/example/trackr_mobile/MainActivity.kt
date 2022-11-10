package com.example.trackr_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
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
                    // Top title bar
                    TitleBar(title = "TrackR")
                    // Dropdown of user's sheets
                    DisplaySheets()
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

@Composable
fun DisplaySheets() {
    var expanded by remember { mutableStateOf(false) }
    // TODO: Will be replaced by backend Sheets API call to fetch the title of the sheet
    val sheets = listOf(
        "Fall Internships",
        "Summer Internships",
        "Winter Internships",
        "Full-time Jobs"
    )
    var selectedText by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val arrowIcon = if (expanded) {
        Icons.Filled.KeyboardArrowDown
    } else {
        Icons.Filled.ArrowDropDown
    }

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(value = selectedText, onValueChange = { selectedText = it },
            modifier = Modifier
                .clickable {  expanded = !expanded  }
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text("Your Sheets") },
            trailingIcon = {
                Icon(arrowIcon, "contentDescription", Modifier.clickable { expanded = !expanded })
            },
            enabled = false,
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            sheets.forEach { sheet ->
                DropdownMenuItem(onClick = {
                    selectedText = sheet
                    expanded = false
                }) {
                    Text(text = sheet)
                }
            }
        }
    }

}

@Composable
fun SheetsListTitle(text: String) {

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TrackrmobileTheme {
        TitleBar(title = "TrackR")
    }
}