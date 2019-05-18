package com.example.todaysweatherforecast

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import kotlinx.coroutines.*

private var toast: Toast? = null


inline fun <reified T : Activity> Activity.startActivityArg(name : String, value : String){
    startActivity(Intent(this, T::class.java).putExtra(name, value))
}


fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo

    return netInfo != null && netInfo.isConnectedOrConnecting
}

object Coroutines {
    fun io(work: suspend (() -> Unit)): Job =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }
    fun <T: Any> ioThenMain(work: suspend (() -> T?), callback: ((T?) -> Unit)): Job =
        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt work()
            }.await()
            callback(data)
        }
}

internal fun Activity.toast(message: CharSequence) {
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .apply { show() }
}