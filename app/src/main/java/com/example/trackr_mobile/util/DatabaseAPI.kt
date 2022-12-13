package com.example.trackr_mobile.util

import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CompletableFuture

@RequiresApi(Build.VERSION_CODES.N)
fun callAPI(userEmail: String): CallbackFuture {
    val client = OkHttpClient()
    val url = "https://efy1mn4ye6.execute-api.us-east-1.amazonaws.com/test"
    val request = Request.Builder().url(url + "/getsheet" +"?email=${userEmail}").build()
    val future = CallbackFuture()
    client.newCall(request).enqueue(future)
    return future
}

@RequiresApi(Build.VERSION_CODES.N)
class CallbackFuture : CompletableFuture<Response?>(),
    Callback {
    override fun onResponse(call: Call?, response: Response?) {
        super.complete(response)
    }

    override fun onFailure(call: Call?, e: IOException?) {
        super.completeExceptionally(e)
    }
}

data class Item(val email: String, val sheetId: String)