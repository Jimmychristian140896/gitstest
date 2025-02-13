package com.jimmy.basecompose.core.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes

fun String.showToast(context: Context, toastLength: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, toastLength).show()
}


fun Context.showToast(@StringRes id: Int, tag: String = "") {
    val errorMessage = getString(id)
    Log.e(tag, "Error: $errorMessage")
    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
}

fun Context.showToast(errorMessage: String, tag: String = "") {
    Log.e(tag, "Error: $errorMessage")
    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
}
