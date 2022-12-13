package com.example.trackr_mobile.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackr_mobile.model.Application
import com.example.trackr_mobile.util.Item
import com.example.trackr_mobile.util.SheetsAPI
import com.example.trackr_mobile.util.callAPI
import com.google.gson.Gson
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun HomeScreen(userEmail: String, displayName: String, context: Context) {
    var applications = remember {
        mutableStateListOf<Application>()
    }

    var loading by remember {
        mutableStateOf(false)
    }
    var spreadsheetId by remember {
        mutableStateOf("")
    }


    var sheetsAPI = SheetsAPI(context, spreadsheetId)
    var onClick = fun  () {
        applications.clear()
        var thread: Thread = Thread {
            loading = true
            var result = callAPI(userEmail).get()
            val gson = Gson()
            spreadsheetId = gson.fromJson(result?.body()?.string(), Item::class.java).sheetId
            sheetsAPI = SheetsAPI(context, spreadsheetId)
            sheetsAPI(sheetsAPI)?.let { applications.addAll(it.get()) }
            loading = false
        }
        thread.start()
    }
    DisplaySheets(applications, sheetsAPI, onClick, loading)


    Text(text = "Welcome $displayName! Your email is $userEmail", modifier = Modifier.offset(x = 20.dp, y = 600.dp))


}



@RequiresApi(Build.VERSION_CODES.N)
fun sheetsAPI(sheetsAPI: SheetsAPI): Future<MutableList<Application>>? {
    val es: ExecutorService = Executors.newFixedThreadPool(10)
    var result: Future<MutableList<Application>>? = null

    try {
        var callable: () -> MutableList<Application> = {sheetsAPI.allEntries}
        result = es.submit(callable)
    } catch (e: Exception) {
        e.printStackTrace();
    }
    return result
}


@Composable
fun TitleBar(title: String) {
    TopAppBar(title = {
        Text(text = title, textAlign = TextAlign.Center)
    })
}

@Composable
fun DisplaySheets(applications: List<Application>?, sheetsAPI: SheetsAPI, onClick: () -> Unit, loading: Boolean) {
    Column {
        TitleBar("TrackR")
        // Status of selected sheet
        if (loading) {
            Text(text = "Loading...")

        } else {
            DisplayStatus(applications, sheetsAPI)
            Button(onClick = onClick, Modifier.padding(20.dp, 0.dp)) {
                Text(text = "TrackR")
            }
        }
    }
}

@Composable
fun DisplayStatus(applications: List<Application>?, sheetsAPI: SheetsAPI) {
    Column(Modifier.padding(20.dp, 0.dp)) {
        if (applications != null) {
            for (i in applications.indices) {
                CustomRadioGroup(applications[i], i, sheetsAPI)
            }
        }
    }
}

@Composable
fun CustomRadioGroup(application: Application, index: Int, sheetsAPI: SheetsAPI) {
    val options = listOf(
        "Applied",
        "Interviewing",
        "Offer",
        "Rejected"
    )
    var company = application.company
    // GET request can populate this variable with what is actually in the sheet
    var selectedOption by remember { mutableStateOf(application.status) }
    val onSelectedChange = { text: String ->
        selectedOption = text
        var thread: Thread = Thread{ sheetsAPI.updateStatus(text, "E" + (index + 2)) }
        thread.start()

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
