package com.example.trackr_mobile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.trackr_mobile.model.User

@Composable
fun HomeScreen(userEmail: String, displayName: String) {
    TitleBar("TrackR")
    DisplaySheets()

    Text(text = "Welcome $displayName! Your email is $userEmail", modifier = Modifier.offset(x = 20.dp, y = 600.dp))
}

@Composable
fun TitleBar(title: String) {
    TopAppBar(title = {
        Text(text = title, textAlign = TextAlign.Center)
    })
    Spacer(modifier = Modifier.padding(30.dp))
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
    val entries = mutableListOf<String>()
    // Eventually use API call to populate list of entries for this selected sheet
    entries.add("Meta")
    entries.add("Google")
    entries.add("Amazon")
    entries.add("Cisco")
    entries.add("Palantir")

    val arrowIcon = if (expanded) {
        Icons.Filled.KeyboardArrowDown
    } else {
        Icons.Filled.ArrowDropDown
    }



    Column(Modifier.padding(20.dp)) {

        Box {
            OutlinedTextField(value = selectedText, onValueChange = { selectedText = it },
                modifier = Modifier
                    .padding(top = 40.dp)
                    .clickable { expanded = !expanded }
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                label = { Text("YouR Sheets") },
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



        // Status of selected sheet
        println(entries)
        DisplayStatus(entries)
    }
}

@Composable
fun DisplayStatus(entries: MutableList<String>) {
    Column {
        for (entry in entries) {
            CustomRadioGroup(company = entry)
        }
    }
}

@Composable
fun CustomRadioGroup(company: String) {
    val options = listOf(
        "Applied",
        "Interviewing",
        "Offer",
        "Rejected"
    )
    // GET request can populate this variable with what is actually in the sheet
    var selectedOption by remember { mutableStateOf("Applied") }
    val onSelectedChange = { text: String ->
        selectedOption = text
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = company, modifier = Modifier.padding(end = 3.dp))
        options.forEach { text ->
            Row(
                modifier = Modifier.padding(all = 2.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.merge(),
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                size = 8.dp,
                            ),
                        )
                        .clickable {
                            // Make a POST to update the Google sheet
                            onSelectedChange(text)
                        }
                        .background(
                            if (text == selectedOption) {
                                Color(0xFF095778)
                            } else {
                                Color.LightGray
                            }
                        )
                        .padding(
                            vertical = 10.dp,
                            horizontal = 10.dp
                        )
                )
            }
        }
    }
}
